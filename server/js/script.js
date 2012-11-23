// JavaScript Document

function format_announcement_form(index) 
{
	if (index == 1) 
	{
		$("#advertisement").show();
		$("#public_service_announcement").hide();
		$("#event").hide();
	} 
	else if (index == 2) 
	{
		$("#advertisement").hide();
		$("#public_service_announcement").show();
		$("#event").hide();
	} 
	else if (index == 3) 
	{
		$("#advertisement").hide();
		$("#public_service_announcement").hide();
		$("#event").show();
	} 
	else 
	{
		$("#advertisement").hide();
		$("#public_service_announcement").hide();
		$("#event").hide();
	}
}