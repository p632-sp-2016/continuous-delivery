ciIntegratorApp.service('IntegratorService', [
		'$http',
		'$log',
		'globalData',
		'$q',
		function($http, $log, globalData, $q) {

			this.createTemplate = function(templateData) {
				var d = $q.defer();
				$log.debug(templateData);
				globalData.dataLoaded = true;
				$http
						.post('integrator-rest/projectbuilder/buildTemplate/',
								templateData).success(
								function(response, status) {
									d.resolve(response);
									$log.debug(status);
									globalData.dataLoaded = false;
								}).error(function() {
							d.reject();
						});

				return d.promise;
			};

			this.loadDependencyList = function() {
				var d = $q.defer();
				$http.get('integrator-rest/projectbuilder/dependencyList')
						.success(function(response) {
							d.resolve(response);
							$log.debug(response);
						}).error(function() {
							d.reject();
						});

				return d.promise;
			};

		} ]);