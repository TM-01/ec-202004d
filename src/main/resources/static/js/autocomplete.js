/**
 * オートコンプリート用
 */

$(function() {
	$("#fuzzyNmae").on("keydown", $(function() {
		$.ajax({
	        url: "http://localhost:8080/autocomplete",
	        dataType: "json",
	        type: "GET"
	    }).then(function(data){
	    	$("#fuzzyName").autocomplete({
	    		maxResults: 5,
	            source: function(request, response) {
	                var results = $.ui.autocomplete.filter(data, request.term);
	                response(results.slice(0, this.options.maxResults));
	            },
	            autoFocus: true,
	            minLength: 1
	        });
	    	console.log(data);
	        },function(){
	    });
	}));
});