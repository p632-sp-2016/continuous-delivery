ciIntegratorApp.controller('templateController', [
		'$scope',
		'$rootScope',
		'IntegratorService',
		'$state',
		function($scope, $rootScope, IntegratorService, $state) {
			$scope.packagingTypeList = [ 'jar', 'war', 'ear' ];
			var dependencyLoadList = IntegratorService.loadDependencyList();

			dependencyLoadList.then(function( val ) {
				$scope.dependencyLoadList = val;
			});
			
			$scope.projectGroup="";
			$scope.artifact="";
			$scope.processForm = function() {

				var templateData = $scope.template;

				IntegratorService.createTemplate(templateData).then(
						function(data) {
							console.log(data);
						});
			};

		} ]);

