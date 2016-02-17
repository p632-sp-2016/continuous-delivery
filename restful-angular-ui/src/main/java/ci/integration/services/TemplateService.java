package ci.integration.services;

import java.io.File;
import java.util.Iterator;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.PushResult;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

@Path("/templateService")
public class TemplateService {

	@GET
	@Path("/test/{param}")
	public Response getMsg(@PathParam("param") String msg) {
		String output = "Jersey say : " + msg;
		System.out.println("output.." + output);
		return Response.status(200).entity(output).build();
	}

	@GET
	@Path("/push/{param}")
	public Response pushGitProject(String artifactName) {

		String username = "kumasuje";
		String password = "Bangalore@57";

		try {
			File file = new File("D:\\test");
			File localPath = File.createTempFile("test", "ing", file);

			localPath.delete();

			// create the directory
			Git git = Git.init().setDirectory(localPath).call();

			Repository repository = FileRepositoryBuilder.create(new File(localPath.getAbsolutePath(), ".git"));

			File myfile = new File(repository.getDirectory().getParent(), "testfile");
			myfile.createNewFile();

			git.add().addFilepattern("testfile").call();

			git.commit().setMessage("Create readme file").call();

			File path = repository.getDirectory();

			CredentialsProvider deatils = new UsernamePasswordCredentialsProvider(username, password);
			Git git1 = Git.open(path);

			System.out.println("Having repository: " + git1.getRepository().getDirectory());

			PushCommand pc = git1.push();

			pc.setCredentialsProvider(deatils).setForce(true).setRemote("https://github.com/kumasuje/new.git")
					.setPushAll();

			Iterator<PushResult> it = pc.call().iterator();
			if (it.hasNext()) {
				System.out.println(it.next().toString());
			}

			System.out.println("done");

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return Response.status(200).entity("success").build();
	}
	
	//to test
	public static void main(String[] args) {
		new TemplateService().pushGitProject("testGit");
	}

}
