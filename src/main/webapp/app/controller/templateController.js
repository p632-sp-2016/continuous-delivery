ciIntegratorApp
		.controller(
				'templateController',
				[
						'$scope',
						'$rootScope',
						'IntegratorService',
						'$state',
						function($scope, $rootScope, IntegratorService, $state) {

							$scope.template = {};
							$scope.packagingTypes = [ 'jar', 'war', 'ear' ];
							console.log($scope.packagingTypes[0])
							$scope.template = {
								packagingType : $scope.packagingTypes[0]
							};

							var dependencyLoadList = IntegratorService
									.loadDependencyList();

							dependencyLoadList.then(function(val) {
								$scope.dependencyLoadList = val;
							});

							$scope.addDependency = function() {
								if (this.template.dependency !== null
										|| this.template.dependency !== " ") {
									$scope.dependencyLoadList
											.push(this.template.dependency);
								}
								this.template.dependency = "";

							}
							$scope.removeDependency = function() {

								if ($scope.template.dependencyList !== null
										&& $scope.template.dependencyList.length > 0) {

									$.each($scope.template.dependencyList,
										function(i, element) {
											console.log($scope.template.dependencyList);
											var index = $scope.dependencyLoadList
													.indexOf(element);
											$scope.dependencyLoadList
													.splice(index,
															1);
									});

								}
							}

							$scope.projectGroup = "";
							$scope.artifact = "";
							$scope.processForm = function() {

								var templateData = $scope.template;
								$rootScope.projectName = templateData.artifact;

								IntegratorService
										.createTemplate(templateData)
										.then(
												function(data) {
													if (data.returnMsg === "success") {
														console.log("sucesss")
														$rootScope.displayingMsgType = "success";
														$rootScope.displayingMsgContent = "Template Created Succesfully";
														$rootScope.gitUrl = "https://github.com/p632-sp-2016/continuous-delivery/"
																+ $rootScope.projectName
																+ ".git";
														$rootScope.bambooUrl = "http://tintin.cs.indiana.edu:8094/browse/"
																+ $rootScope.projectName;
														$scope.template = {};

														$("#successAlert")
																.show();
													} else {
														console.log("error");
														
														$("#failureAlert")
																.show();
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
