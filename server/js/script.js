// JavaScript Document

function check_authentication()
{
	if (!sessionStorage.username)
		window.location.href = "login.html";
	
	if (sessionStorage.username == "")
		window.location.href = "login.html";
}

function logout()
{
	sessionStorage.username = "";
	window.location.href = "login.html";
}

function init_main_page()
{
	check_authentication();
	$("#active_user").html(sessionStorage.username);
}

function init_account_page()
{
	AJAXManager.get_profile(init_profile);
}

function init_profile(profile)
{
	var form = document.forms["profile_form"];
	form.name.value = profile.name;
	form.street_address.value = profile.street_address;
	form.city.value = profile.city;
	form.state.value = profile.state;
	form.zipcode.value = profile.zipcode;
	form.website.value = profile.website;
	form.email.value = profile.email;
	form.about.value = profile.about;
}

function init_announcement_list()
{
	AJAXManager.get_announcement(init_announcement_listview);
}

function init_announcement_listview(announcements)
{
	$("#announcement_list").empty();
	for (var index in announcements)
	{
		var announcement = announcements[index];
		
		var template = "<div id='announcement" + announcement.id + "' class='announcement_item'>";
		template += "<table class='table .table-condensed'>";
		
		template += "<tr>";
		template += "<th>Title</th>";
		template += "<td>" + announcement.title + "</td>";
		template += "</tr>";
		
		template += "<tr>";
		template += "<th>Type</th>";
		template += "<td>" + announcement.type + "</td>";
		template += "</tr>";
		
		template += "<tr>";
		template += "<th>Highlights</th>";
		template += "<td>" + announcement.highlights + "</td>";
		template += "</tr>";
		
		if (announcement.fine_print != "")
		{
			template += "<tr>";
			template += "<th>Fine print</th>";
			template += "<td>" + announcement.fine_print + "</td>";
			template += "</tr>";
		}
		
		template += "<tr>";
		template += "<th>Address</th>";
		template += "<td>";
		template += announcement.street_address + "<br />"; 
		template += announcement.city + ", "; 
		template += announcement.state + " "; 
		template += announcement.zipcode; 
		template += "</td>";
		template += "</tr>";
		
		template += "<tr>";
		template += "<th>Radius</th>";
		template += "<td>" + announcement.radius + "</td>";
		template += "</tr>";
		
		if (announcement.regular_price != "")
		{
			template += "<tr>";
			template += "<th>Regular Price</th>";
			template += "<td>" + announcement.regular_price + "</td>";
			template += "</tr>";
		}
		
		if (announcement.promotional_price != "")
		{
			template += "<tr>";
			template += "<th>Promotional Price</th>";
			template += "<td>" + announcement.promotional_price + "</td>";
			template += "</tr>";
		}
		
		template += "<tr>";
		template += "<th>From</th>";
		template += "<td>" + announcement.from_date + "</td>";
		template += "</tr>";
		
		template += "<tr>";
		template += "<th>To</th>";
		template += "<td>" + announcement.to_date + "</td>";
		template += "</tr>";
		
		if (announcement.url != "")
		{
			template += "<tr>";
			template += "<th>URL</th>";
			template += "<td>" + announcement.url + "</td>";
			template += "</tr>";
		}
		
		template += "<tr>";
		template += "<th>Category</th>";
		template += "<td>" + announcement.category + "</td>";
		template += "</tr>";

		template += "</table>";
		
		template += "<div class='annoucement_list_control'>";
		template += "<form onSubmit='return false;' class='form-horizontal'>";
		template += "<input type='hidden' name='announcement_id' value='" + announcement.id + "'>";
		template += "<button class='btn btn-primary' onClick=''>Update</button>";
		template += "<button class='btn btn-danger' onClick='AJAXManager.delete_announcement(this);'>Delete!</button>";		
		template += "</form>";
		template += "</div>";
		
		template += "</div>";
		$("#announcement_list").append(template);
	}
}

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