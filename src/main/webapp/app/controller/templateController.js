ciIntegratorApp
		.controller(
				'templateController',
				[
						'$scope',
						'$rootScope',
						'IntegratorService',
						'$state',
						function($scope, $rootScope, IntegratorService, $state) {
							$scope.packagingTypeList = [ 'jar', 'war', 'ear' ];
							var dependencyLoadList = IntegratorService
									.loadDependencyList();

							dependencyLoadList.then(function(val) {
								$scope.dependencyLoadList = val;
							});

							$scope.projectGroup = "";
							$scope.artifact = "";
							$scope.processForm = function() {

								var templateData = $scope.template;

								IntegratorService
										.createTemplate(templateData)
										.then(
												function(data) {
													if (data.returnMsg=="success") {
														$rootScope.displayingMsgType = "success";
														$rootScope.displayingMsgContent = "Template Created Succesfully";
														$scope.template={};
													}

												});
							};

							var template = angular.copy($scope.template);

							$scope.resetForm = function() {
								$scope.template = angular.copy(template);
								$scope.templateForm.$setPristine();
							};

							$scope.isTemplateChanged = function() {
								return !angular.equals($scope.template,
										template);
							};

						} ]);
