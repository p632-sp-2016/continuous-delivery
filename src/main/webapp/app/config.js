ciIntegratorApp.config([
		'$stateProvider',
		'$urlRouterProvider',
		'$httpProvider',
		'$logProvider',
		function($stateProvider, $urlRouterProvider, $httpProvider,$logProvider) {


			$urlRouterProvider.otherwise('/createTemplate');

			$stateProvider

			.state('createTemplate', {
				url : '/createTemplate',
				templateUrl : 'partials/createTemplate.html',
				controller : 'templateController'

			}).state('projectDetails', {
				url : '/projectDetails.html',
				templateUrl : 'projectDetails.html',
				controller : 'templateDetailController'

			})
			
		} ]);
