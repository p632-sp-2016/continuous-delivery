ciIntegratorApp.service('IntegratorService', [
		'$http',
		'$log',
		'$q',
		function($http, $log, $q) {

			this.createTemplate = function(templateData) {
				var d = $q.defer();
				$log.debug(templateData);
				$.LoadingOverlay("show");
				$http.post('integrator-rest/projectbuilder/buildTemplate/',
						templateData).success(function(response, status) {
					d.resolve(response);
					$log.debug(status);
					$.LoadingOverlay("hide");
				}).error(function(response) {
					$.LoadingOverlay("hide");
					d.reject(response);
				});
				
				return d.promise;
			};

			this.loadDependencyList = function() {
				var d = $q.defer();
				$http.get('integrator-rest/projectbuilder/dependencyList')
						.success(function(response,status) {
							d.resolve(response);
							$log.debug(response);
						}).error(function(response) {
							d.reject(response);
						});

				return d.promise;
			};

		} ]);