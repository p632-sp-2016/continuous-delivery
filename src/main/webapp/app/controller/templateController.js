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
							// console.log($scope.packagingTypes[0])
							$scope.template = {
								packagingType : $scope.packagingTypes[0]
							};

							var dependencyLoadList = IntegratorService
									.loadDependencyList();

							dependencyLoadList.then(function(val) {
								$scope.dependencyLoadList = val;
							});

							$scope.addDependency = function($event) {
								
								if ((this.template.artifactId != undefined)
										&& (this.template.groupId != undefined)) {
									$scope.dependencyLoadList
											.push(this.template.groupId + ":"
													+ this.template.artifactId);
								}
								this.template.groupId = "";
								this.template.artifactId = "";
								$event.preventDefault();

							}
							$scope.removeDependency = function($event) {

								if ($scope.template.dependencyList != null
										&& $scope.template.dependencyList.length > 0) {

									$
											.each(
													$scope.template.dependencyList,
													function(i, element) {
														var index = $scope.dependencyLoadList
																.indexOf(element);
														$scope.dependencyLoadList
																.splice(index,
																		1);
													});

								}
								$event.preventDefault();
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
													console.log(data);
													if (data != undefined
															&& data.returnMsg == "success") {
														console.log("sucesss")
														$rootScope.displayingMsgContent = "Template Created Succesfully";
														$rootScope.gitUrl = "https://github.iu.edu/"
																+ data.organizationName
																+ "/"
																+ $rootScope.projectName
																+ ".git";
														$rootScope.bambooUrl = "http://tintin.cs.indiana.edu:8094/browse/"
																+ $rootScope.projectName.toUpperCase();
														$scope.template = {};
														
														$("input").removeClass("ng-touched");

														$("#successAlert")
																.show();
													} else {
														console.log("error");
														$rootScope.errorContent = data.errMsg;
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
