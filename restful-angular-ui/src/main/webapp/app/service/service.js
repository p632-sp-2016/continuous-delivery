ciIntegratorApp.service(
		'IntegratorService',
		[
				'$http',
				'$log',
				'$q',
				function($http, $log, $q) {

					this.createTemplate = function(templateData) {
						var d = $q.defer();
						$log.debug('templateData' + templateData);
						console.log(templateData.projectGroup);
						$http.get(
								'/integrator-rest/templateService/push/'+ templateData.artifact).success(function(response) {
							d.resolve(response);
							$log.debug("success" + response);
						}).error(function() {
							d.reject();
						});

						return d.promise;
					};
					
					this.loadDependencyList = function() {
						var d = $q.defer();
						$http.get(
								'/integrator-rest/templateService/dependencyList').success(function(response) {
							d.resolve(response);
							console.log(response);
							$log.debug("success" + response);
						}).error(function() {
							d.reject();
						});

						return d.promise;
					};
					
				} ]);