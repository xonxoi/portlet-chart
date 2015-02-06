var barChart = null;
var ctx = null;
var init = false;

$(function() {
	
    ctx = $("#nitecoChart").get(0).getContext("2d");
    var tbl = $("#tblEmployee");
    
    if($(tbl).length == 0){
    	$("#nitecoChart").remove();
    	$("#barArea").append('<div class="alert alert-danger">'
    			+ '<strong>Not Found!</strong> Data source is not found.'
    	 	+'</div>');
    } else {
    	displayBarChart();
    	
    	$("#dataSource").change(function(){
        	displayBarChart();
        });
    }
    
    
});

function displayBarChart() {

	var src = $("#dataSource").val();
	var _data = [];
	var _label = [];
	
	if(src == "employee"){
		$("#tblEmployee tbody tr td:nth-child(2)").each(function(){
			_label.push($(this).text());
		});
		$("#tblEmployee tbody tr td:nth-child(5)").each(function(){
			_data.push($(this).text());
		});
	} else {
		$("#tblEmployee tbody tr td:nth-child(6)").each(function(){
			var city = $.trim($(this).text());
			var idx = $.inArray(city, _label);
			if(idx < 0){
				_label.push(city);
				_data.push(1);
			}
			else{
				_data[idx]++;
			}
		});
	}
	
	var data = {
			labels : _label,
			datasets : [{
				label : "My Second dataset",
				fillColor : "rgba(151,187,205,0.5)",
				strokeColor : "rgba(151,187,205,0.8)",
				highlightFill : "rgba(151,187,205,0.75)",
				highlightStroke : "rgba(151,187,205,1)",
				data : _data
			}]
		};
	
	if( init ){
		barChart.destroy();
	}
	barChart = new Chart(ctx).Bar(data,{});
	init = true;
}
