/*
 * ANGULARJS 
 */

angular.module('leads', [])

	.controller('MainController', function($scope, $http, $timeout) {

	var local_server_url = "http://localhost:8080";
	$scope.leads=[]
	$scope.totalLeads=0
	pageRange=0

	var findTotalLeads = function(){
	    $http.get(local_server_url + "/leads/total").then(function(response){
	    	/* SUCESSO */
	    	$scope.totalLeads = response.data
	    	//console.log('found leads total: ' + response.data)
	    }, function(response){
	    	/* ERRO */
	    	console.log('error....')
	    	console.log(response)
	    });
	  }

	var findLeads = function(pageRange){
	    $http.get(local_server_url + "/leads?pageRange="+pageRange).then(function(response){
	    	/* SUCESSO */
	    	$scope.leads = response.data
	    	//console.log(response.data)
	    	findTotalLeads()
	    }, function(response){
	    	/* ERRO */
	    	console.log('error....')
	    	console.log(response)
	    });
	  }
	
	$scope.drop = function(){
	    $http.get(local_server_url + "/leads/drop").then(function(response){
	    	/* SUCESSO */
	    	//console.log('all data dropped')
	    	findLeads(-1);
	    }, function(response){
	    	/* ERRO */
	    	console.log('error dropping')
	    });
	}

	        
	$scope.BOL = true
	$scope.EOL = false
  $scope.leadsGetNext = function() {
		if (!$scope.EOL) {
	    pageRange++
	    findLeads(pageRange);
	  }
	  $scope.BOL = (pageRange < 1)
	  $scope.EOL = $scope.totalLeads / 5 - pageRange < 1
	}
	

	$scope.leadsGetPrevious = function() {
    if (pageRange > 0 ) {
	    pageRange--
	    findLeads(pageRange);
    }
    $scope.BOL = (pageRange < 1)
    $scope.EOL = $scope.totalLeads / 5 - pageRange < 1
  }

	findLeads(pageRange);
});

