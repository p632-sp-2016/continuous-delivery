ciIntegratorApp.controller('templateController', [
		'$scope',
		'$rootScope',
		'IntegratorService',
		'$state',
		function($scope, $rootScope, IntegratorService, $state) {
			console.log("sadasdads");

			$scope.packagingTypeList = [ 'jar', 'war', 'ear' ];
			var dependencyLoadList = IntegratorService.loadDependencyList();
			$scope.dependencyLoadList = ['abc','hhhh'];
			$scope.projectGroup="";
			$scope.artifact="";

			$scope.processForm = function() {

				var templateData = $scope.template;

				IntegratorService.createTemplate(templateData).then(
						function(data,status) {
							console.log(data);
							if (status == 200) {
								$rootScope.templateDetails=data;
								$state.go("projectDetails");
							} else {
								$state.go("createTemplate");
							}
						});
			};

		} ]);

ciIntegratorApp.controller('templateDetailController', [ '$scope',
		'$rootScope', '$state', function($scope, $rootScope, $state) {

			var templateDetails = $rootScope.templateDetails;
			if (templateDetails != null) {
				$scope.gitUrl = templateDetails.gitUrl;
			}

		} ]);