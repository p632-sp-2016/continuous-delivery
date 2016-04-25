ciIntegratorApp.service('IntegratorService', [
		'$http',
		'$log',
		'$q',
		function($http, $log, $q) {

			this.createTemplate = function(templateData) {
				var d = $q.defer();
				$log.debug(templateData);
				//$.LoadingOverlay("show");
				//$('#target').loadingOverlay();
				$('#myModal').modal('show');
				$http.post('integrator-rest/projectbuilder/buildTemplate/',
						templateData).success(function(response, status) {
					d.resolve(response);
					$log.debug(status);
					
					//$('#target').loadingOverlay('remove');
					//$.LoadingOverlay("hide");
					$('#myModal').modal('hide');
				}).error(function() {
					$('#myModal').modal('hide');
					//$.LoadingOverlay("hide");
					//$('#target').loadingOverlay('remove');
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