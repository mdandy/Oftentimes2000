// JavaScript Document

function get_URL_Parameter(name) 
{
	return decodeURI((RegExp(name + '=' + '(.+?)(&|$)').exec(location.search)||[,null])[1]);
}
	
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
	sessionStorage.street_address = "";
	sessionStorage.city = "";
	sessionStorage.state = "";
	sessionStorage.zipcode = "";
	sessionStorage.website = "";
	
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
		template += "<th class='announcement_title'>Title</th>";
		template += "<td>" + announcement.title + "</td>";
		template += "</tr>";
		
		template += "<tr>";
		template += "<th class='announcement_title'>Type</th>";
		template += "<td>" + to_type_string(announcement.type) + "</td>";
		template += "</tr>";
		
		template += "<tr>";
		template += "<th class='announcement_title'>Highlights</th>";
		template += "<td>" + announcement.highlights + "</td>";
		template += "</tr>";
		
		if (announcement.fine_print != null)
		{
			template += "<tr>";
			template += "<th class='announcement_title'>Fine print</th>";
			template += "<td>" + announcement.fine_print + "</td>";
			template += "</tr>";
		}
		
		template += "<tr>";
		template += "<th class='announcement_title'>Address</th>";
		template += "<td>";
		template += announcement.street_address + "<br />"; 
		template += announcement.city + ", "; 
		template += announcement.state + " "; 
		template += announcement.zipcode; 
		template += "</td>";
		template += "</tr>";
		
		template += "<tr>";
		template += "<th class='announcement_title'>Radius</th>";
		template += "<td>" + announcement.radius + "</td>";
		template += "</tr>";
		
		if (announcement.regular_price != null)
		{
			template += "<tr>";
			template += "<th class='announcement_title'>Regular Price</th>";
			template += "<td>" + announcement.regular_price + "</td>";
			template += "</tr>";
		}
		
		if (announcement.promotional_price != null)
		{
			template += "<tr>";
			template += "<th class='announcement_title'>Promotional Price</th>";
			template += "<td>" + announcement.promotional_price + "</td>";
			template += "</tr>";
		}
		
		template += "<tr>";
		template += "<th class='announcement_title'>From</th>";
		template += "<td>" + announcement.from_date + "</td>";
		template += "</tr>";
		
		template += "<tr>";
		template += "<th class='announcement_title'>To</th>";
		template += "<td>" + announcement.to_date + "</td>";
		template += "</tr>";
		
		if (announcement.url != null)
		{
			template += "<tr>";
			template += "<th class='announcement_title'>URL</th>";
			template += "<td>" + announcement.url + "</td>";
			template += "</tr>";
		}
		
		template += "<tr>";
		template += "<th class='announcement_title'>Category</th>";
		template += "<td>" + announcement.category + "</td>";
		template += "</tr>";

		template += "</table>";
		
		template += "<div class='annoucement_list_control'>";
		template += "<form onSubmit='return false;' class='form-horizontal'>";
		template += "<input type='hidden' name='announcement_id' value='" + announcement.id + "'>";
		template += "<button class='btn btn-primary' onClick='init_update_announcement(this);'>Update</button>";
		template += "<button class='btn btn-danger' onClick='AJAXManager.delete_announcement(this);'>Delete!</button>";		
		template += "</form>";
		template += "</div>";
		
		template += "</div>";
		$("#announcement_list").append(template);
	}
}

function to_type_string(type)
{
	if (type == 1)
		return "Advertisement";
	else if (type == 2)
		return "Public Service Announcement (PSA)"
	else if (type == 3)
		return "Event";	
}

function init_update_announcement(element)
{
	var form = $(element).parent();
	var id = $(form).find("input[name='announcement_id']").val();
	window.location.href = "create_announcement.html?id=" + id;
}

function init_create_announcement()
{
	var id = get_URL_Parameter("id");
	if (id != "null")
	{
		AJAXManager.get_announcement_by_id(id, init_create_announcement_data);
	}
	else
	{
		// Pre-initialized the form
		if (!sessionStorage.street_address || sessionStorage.street_address == "")
			AJAXManager.get_profile(init_create_announcement_perpopulate);
		else
			init_create_announcement_perpopulate();
	}
}

function init_create_announcement_perpopulate()
{
	var street_address = sessionStorage.street_address;
	var city = sessionStorage.city;
	var state = sessionStorage.state;
	var zipcode = sessionStorage.zipcode;
	var url = sessionStorage.website;
	
	var form = document.forms["create_advertisement_form"];
	form.ads_street_address.value = street_address;
	form.ads_city.value = city;
	form.ads_state.value = state;
	form.ads_zipcode.value = zipcode;
	form.ads_url.value = url;
	
	form = document.forms["create_psa_form"];
	form.psa_street_address.value = street_address;
	form.psa_city.value = city;
	form.psa_state.value = state;
	form.psa_zipcode.value = zipcode;
	form.psa_url.value = url;
	
	form = document.forms["create_event_form"];
	form.event_street_address.value = street_address;
	form.event_city.value = city;
	form.event_state.value = state;
	form.event_zipcode.value = zipcode;
	form.event_url.value = url;
	
	format_announcement_form(0);
}

function init_create_announcement_data(announcement)
{
	format_announcement_form(announcement.type);
	
	if (announcement.type == 1)
	{
		var form = document.forms["create_advertisement_form"];
		form.ads_id.value = announcement.id;
		form.ads_title.value = announcement.title;
		form.ads_highlights.value = announcement.highlights;
		form.ads_fine_print.value = announcement.fine_print;
		form.ads_street_address.value = announcement.street_address;
		form.ads_city.value = announcement.city;
		form.ads_state.value = announcement.state;
		form.ads_zipcode.value = announcement.zipcode;
		form.ads_radius.value = announcement.radius;
		form.ads_regular_price.value = announcement.regular_price;
		form.ads_promotional_price.value = announcement.promotional_price;
		form.ads_from.value = to_date(announcement.from_date);
		form.ads_to.value = to_date(announcement.to_date);
		form.ads_url.value = announcement.url;
		form.ads_category.value = announcement.category;
	}
	else if (announcement.type == 2)
	{
		var form = document.forms["create_psa_form"];
		form.psa_id.value = announcement.id;
		form.psa_title.value = announcement.title;
		form.psa_highlights.value = announcement.highlights;
		form.psa_street_address.value = announcement.street_address;
		form.psa_city.value = announcement.city;
		form.psa_state.value = announcement.state;
		form.psa_zipcode.value = announcement.zipcode;
		form.psa_radius.value = announcement.radius;
		form.psa_from.value = to_date(announcement.from_date);
		form.psa_from_time.value = to_time(announcement.from_date);
		form.psa_to.value = to_date(announcement.to_date);
		form.psa_to_time.value = to_time(announcement.to_date);
		form.psa_url.value = announcement.url;
		form.psa_category.value = announcement.category;
	}
	else if (announcement.type == 3)
	{
		var form = document.forms["create_event_form"];
		form.event_id.value = announcement.id;
		form.event_title.value = announcement.title;
		form.event_highlights.value = announcement.highlights;
		form.event_street_address.value = announcement.street_address;
		form.event_city.value = announcement.city;
		form.event_state.value = announcement.state;
		form.event_zipcode.value = announcement.zipcode;
		form.event_radius.value = announcement.radius;
		form.event_from.value = to_date(announcement.from_date);
		form.event_from_time.value = to_time(announcement.from_date);
		form.event_to.value = to_date(announcement.to_date);
		form.event_to_time.value = to_time(announcement.to_date);
		form.event_url.value = announcement.url;
		form.event_category.value = announcement.category;
	}
}

function to_date(date)
{
	// 2012-11-25 23:59:59
	var temp = date.split(" ");
	return temp[0];
}

function to_time(date)
{
	// 2012-11-25 23:59:59
	var temp = date.split(" ");
	return temp[1];
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