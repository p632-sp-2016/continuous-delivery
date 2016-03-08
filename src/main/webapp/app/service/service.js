ciIntegratorApp.service('IntegratorService', [
		'$http',
		'$log',
		'$q',
		function($http, $log, $q) {

			this.createTemplate = function(templateData) {
				var d = $q.defer();
				$log.debug(templateData);
				$http.post('integrator-rest/test/createTemplate/', templateData)
						.success(function(response) {
							d.resolve(response);
							$log.debug(response);
						}).error(function() {
							d.reject();
						});

				return d.promise;
			};

			this.loadDependencyList = function() {
				var d = $q.defer();
				$http.get('integrator-rest/projectbuilder/dependencyList').success(
						function(response) {
							d.resolve(response);
							$log.debug(response);
						}).error(function() {
					d.reject();
				});

				return d.promise;
			};

		} ]);