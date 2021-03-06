// JavaScript Document

var AJAXManager =
{
	url:				"http://m2.cip.gatech.edu/d/maven/api/Oftentimes2000/",
	
	login: function()
	{
		var form = document.forms["signin_form"];
		var username = form.username.value;
		var password = form.password.value;
	
		$.ajaxSetup ({
			cache: false
		});

		var loadUrl = AJAXManager.url + "login";
		var query = { username: username, 
					  password: password };

		$.ajax ({
			type: "POST",
			url: loadUrl,
			data: query,
			dataType: "json",
			timeout: 5000, //5 seconds
			success: function(data) 
			{
				// Save in Web Storage
				if (data.res == "TRUE")
				{
					sessionStorage.username = data.data.username;
					window.location.href = "index.html";
				}
				else
				{
					sessionStorage.username = "";
					
					var template = "<div class='alert alert-error'>";
					template += "<button type='button' class='close' data-dismiss='alert'>×</button>";
					template += "<strong>Error!</strong> Invalid username and/ or password.</div>";
					$("#login_alert").append(template);
				}
			},
			error: function(jqXHR, textStatus, errorThrown) 
			{
				console.error(textStatus);
			}
		});
	},
	
	register: function()
	{
		var form = document.forms["signup_form"];
		var username = form.username.value;
		var password = form.password.value;
		var password2 = form.password2.value;
		
		if (password !== password2)
		{
			var template = "<div class='alert alert-error'>";
			template += "<button type='button' class='close' data-dismiss='alert'>×</button>";
			template += "<strong>Error!</strong> Password does not match.</div>";
			$("#signup_alert").append(template);
			return;
		}
		
		if (username == "" || password == "")
		{
			var template = "<div class='alert alert-error'>";
			template += "<button type='button' class='close' data-dismiss='alert'>×</button>";
			template += "<strong>Error!</strong> Username and/ or password cannot be empty.</div>";
			$("#signup_alert").append(template);
			return;
		}
	
		$.ajaxSetup ({
			cache: false
		});

		var loadUrl = AJAXManager.url + "user/account";
		var query = { username: username, 
					  password: password };

		$.ajax ({
			type: "POST",
			url: loadUrl,
			data: query,
			dataType: "json",
			timeout: 5000, //5 seconds
			success: function(data) 
			{
				if (data.res == "TRUE")
				{
					sessionStorage.username = username;
					window.location.href = "index.html";
				}
				else
				{
					var template = "<div class='alert alert-error'>";
					template += "<button type='button' class='close' data-dismiss='alert'>×</button>";
					template += "<strong>Error!</strong> Unable to create account.</div>";
					$("#signup_alert").append(template);
				}
			},
			error: function(jqXHR, textStatus, errorThrown) 
			{
				console.error(textStatus);
			}
		});
	},
	
	update_account: function()
	{
		var form = document.forms["account_form"];
		var username = sessionStorage.username;
		var password = form.password.value;
		var password2 = form.password2.value;
		
		if (password !== password2)
		{
			var template = "<div class='alert alert-error'>";
			template += "<button type='button' class='close' data-dismiss='alert'>×</button>";
			template += "<strong>Error!</strong> Password does not match.</div>";
			$("#account_alert").append(template);
			return;
		}
		
		if (password == "")
		{
			var template = "<div class='alert alert-error'>";
			template += "<button type='button' class='close' data-dismiss='alert'>×</button>";
			template += "<strong>Error!</strong> Username and/ or password cannot be empty.</div>";
			$("#account_alert").append(template);
			return;
		}
	
		$.ajaxSetup ({
			cache: false
		});

		var loadUrl = AJAXManager.url + "user/account";
		var query = { username: username, 
					  password: password };

		$.ajax ({
			type: "POST",
			url: loadUrl,
			data: query,
			dataType: "json",
			timeout: 5000, //5 seconds
			success: function(data) 
			{
				if (data.res == "TRUE")
				{
					var template = "<div class='alert alert-success'>";
					template += "<button type='button' class='close' data-dismiss='alert'>×</button>";
					template += "<strong>Success!</strong> Your password has been changed.</div>";
					$("#account_alert").append(template);
				}
				else
				{
					var template = "<div class='alert alert-error'>";
					template += "<button type='button' class='close' data-dismiss='alert'>×</button>";
					template += "<strong>Error!</strong> Unable to change your password.</div>";
					$("#account_alert").append(template);
				}
			},
			error: function(jqXHR, textStatus, errorThrown) 
			{
				console.error(textStatus);
			}
		});
	},
	
	get_profile: function (callback)
	{
		var username = sessionStorage.username;
		
		$.ajaxSetup ({
			cache: true
		});

		var loadUrl = AJAXManager.url + "user/" + username;
	
		$.ajax ({
			type: "GET",
			url: loadUrl,
			dataType: "json",
			timeout: 5000, //5 seconds
			success: function(data) 
			{
				if (data.res == "TRUE")
				{
					// Save in the session storage
					sessionStorage.street_address = data.data.street_address;
					sessionStorage.city = data.data.city;
					sessionStorage.state = data.data.state;
					sessionStorage.zipcode = data.data.zipcode;
					sessionStorage.website = data.data.website;
					
					callback(data.data);
				}
			},
			error: function(jqXHR, textStatus, errorThrown) 
			{
				console.error(textStatus);
			}
		});
	},
	
	update_profile: function()
	{
		var form = document.forms["profile_form"];
		var username = sessionStorage.username;
		var name = form.name.value;
		var street_address = form.street_address.value;
		var city = form.city.value;
		var state = form.state.value;
		var zipcode = form.zipcode.value;
		var website = form.website.value;
		var email = form.email.value;
		var about = form.about.value;
			
		if (name == "" || street_address == "" || city == "" || state == "" || zipcode == "" || email == "" || about == "")
		{
			var template = "<div class='alert alert-error'>";
			template += "<button type='button' class='close' data-dismiss='alert'>×</button>";
			template += "<strong>Error!</strong> Required fields cannot be empty.</div>";
			$("#profile_alert").append(template);
			return;
		}
	
		$.ajaxSetup ({
			cache: false
		});

		var loadUrl = AJAXManager.url + "user/profile";
		var query = { username: username, 
					  name: name, 
					  street_address: street_address, 
					  city: city, 
					  state: state, 
					  zipcode: zipcode, 
					  website: website, 
					  email: email, 
					  about: about };

		$.ajax ({
			type: "POST",
			url: loadUrl,
			data: query,
			dataType: "json",
			timeout: 5000, //5 seconds
			success: function(data) 
			{
				if (data.res == "TRUE")
				{
					var template = "<div class='alert alert-success'>";
					template += "<button type='button' class='close' data-dismiss='alert'>×</button>";
					template += "<strong>Success!</strong> Your profile has been updated.</div>";
					$("#profile_alert").append(template);
				}
				else
				{
					var template = "<div class='alert alert-error'>";
					template += "<button type='button' class='close' data-dismiss='alert'>×</button>";
					template += "<strong>Error!</strong> Unable to update your profile.</div>";
					$("#profile_alert").append(template);
				}
			},
			error: function(jqXHR, textStatus, errorThrown) 
			{
				console.error(textStatus);
			}
		});
	},
	
	get_announcement: function (callback)
	{
		var username = sessionStorage.username;
		
		$.ajaxSetup ({
			cache: true
		});
		var ajax_load = "<p><img src=\"image/ajax-loader.gif\" /> Loading...</p>";
		var loadUrl = AJAXManager.url + "announcement/" + username;
	
		$("#announcement_list_alert").html(ajax_load);
		$.ajax ({
			type: "GET",
			url: loadUrl,
			dataType: "json",
			timeout: 5000, //5 seconds
			success: function(data) 
			{
				$("#announcement_list_alert").empty();
				if (data.res == "TRUE")
				{
					callback(data.data);
				}
				else
				{
					var template = "<div class='alert alert-block'>";
					template += "<button type='button' class='close' data-dismiss='alert'>×</button>";
					template += "<strong>Not found!</strong> You don't have any annoncement yet.</div>";
					$("#announcement_list_alert").append(template);
				}
			},
			error: function(jqXHR, textStatus, errorThrown) 
			{
				$("#announcement_list_alert").empty();
				console.error(textStatus);
			}
		});
	},
	
	get_announcement_by_id: function (id, callback)
	{	
		$.ajaxSetup ({
			cache: true
		});
		
		var loadUrl = AJAXManager.url + "announcement/" + id;
		$.ajax ({
			type: "GET",
			url: loadUrl,
			dataType: "json",
			timeout: 5000, //5 seconds
			success: function(data) 
			{
				if (data.res == "TRUE")
				{
					callback(data.data);
				}
			},
			error: function(jqXHR, textStatus, errorThrown) 
			{
				console.error(textStatus);
			}
		});
	},
	
	delete_announcement: function (element)
	{
		var form = $(element).parent();
		var id = $(form).find("input[name='announcement_id']").val();
		
		$.ajaxSetup ({
			cache: false
		});

		var loadUrl = AJAXManager.url + "announcement/delete?id=" + id;
	
		$.ajax ({
			type: "GET",
			url: loadUrl,
			dataType: "json",
			timeout: 5000, //5 seconds
			success: function(data) 
			{
				if (data.res == "TRUE")
				{
					$("#announcement" + id).remove();
					
					var template = "<div class='alert alert-success'>";
					template += "<button type='button' class='close' data-dismiss='alert'>×</button>";
					template += "<strong>Success!</strong> Your announcement has been deleted.</div>";
					$("#announcement_list_alert").append(template);
				}
			},
			error: function(jqXHR, textStatus, errorThrown) 
			{
				console.error(textStatus);
			}
		});
	},
	
	create_announcement: function(type)
	{
		if (type == "advertisement")
		{
			var form = document.forms["create_advertisement_form"];
			var username = sessionStorage.username;
			var id = form.ads_id.value;
			var title = form.ads_title.value;
			var highlights = form.ads_highlights.value;
			var fine_print = form.ads_fine_print.value;
			var street_address = form.ads_street_address.value;
			var city = form.ads_city.value;
			var state = form.ads_state.value;
			var zipcode = form.ads_zipcode.value;
			var radius = form.ads_radius.value;
			var regular_price = form.ads_regular_price.value;
			var promotional_price = form.ads_promotional_price.value;
			var from = form.ads_from.value;
			var to = form.ads_to.value;
			var url = form.ads_url.value;
			var category = form.ads_category.value;
			
			$.ajaxSetup ({
				cache: false
			});
	
			var loadUrl = AJAXManager.url + "announcement/advertisement";
			var query = { username: username,
						  id: id,
						  title: title,
						  highlights: highlights,
						  fine_print: fine_print,
					  	  street_address: street_address, 
					  	  city: city, 
					  	  state: state, 
					  	  zipcode: zipcode,
						  radius: radius,
						  regular_price: regular_price,
						  promotional_price: promotional_price,
						  from: from,
						  to: to,
					  	  url: url, 
					  	  category: category };
					  
			$.ajax ({
				type: "POST",
				url: loadUrl,
				data: query,
				dataType: "json",
				timeout: 5000, //5 seconds
				success: function(data) 
				{
					if (data.res == "TRUE")
					{
						var template = "<div class='alert alert-success'>";
						template += "<button type='button' class='close' data-dismiss='alert'>×</button>";
						template += "<strong>Success!</strong> Your announcement has been created/ updated.</div>";
						$("#create_announcement_alert").append(template);
					}
					else
					{
						var template = "<div class='alert alert-error'>";
						template += "<button type='button' class='close' data-dismiss='alert'>×</button>";
						template += "<strong>Error!</strong> Unable to create/ update your announcement.</div>";
						$("#create_announcement_alert").append(template);
					}
				},
				error: function(jqXHR, textStatus, errorThrown) 
				{
					console.error(textStatus);
				}
			});
		}
		else if (type == "psa")
		{
			var form = document.forms["create_psa_form"];
			var username = sessionStorage.username;
			var id = form.psa_id.value;
			var title = form.psa_title.value;
			var highlights = form.psa_highlights.value;
			var street_address = form.psa_street_address.value;
			var city = form.psa_city.value;
			var state = form.psa_state.value;
			var zipcode = form.psa_zipcode.value;
			var radius = form.psa_radius.value;
			var from = form.psa_from.value + " " + form.psa_from_time.value;
			var to = form.psa_to.value + " " + form.psa_to_time.value;
			var url = form.psa_url.value;
			var category = form.psa_category.value;
			
			$.ajaxSetup ({
				cache: false
			});
	
			var loadUrl = AJAXManager.url + "announcement/psa";
			var query = { username: username,
						  id: id,
						  title: title,
						  highlights: highlights,
					  	  street_address: street_address, 
					  	  city: city, 
					  	  state: state, 
					  	  zipcode: zipcode,
						  radius: radius,
						  from: from,
						  to: to,
					  	  url: url, 
					  	  category: category };
					  
			$.ajax ({
				type: "POST",
				url: loadUrl,
				data: query,
				dataType: "json",
				timeout: 5000, //5 seconds
				success: function(data) 
				{
					if (data.res == "TRUE")
					{
						var template = "<div class='alert alert-success'>";
						template += "<button type='button' class='close' data-dismiss='alert'>×</button>";
						template += "<strong>Success!</strong> Your announcement has been created/ updated.</div>";
						$("#create_announcement_alert").append(template);
					}
					else
					{
						var template = "<div class='alert alert-error'>";
						template += "<button type='button' class='close' data-dismiss='alert'>×</button>";
						template += "<strong>Error!</strong> Unable to create/ update your announcement.</div>";
						$("#create_announcement_alert").append(template);
					}
				},
				error: function(jqXHR, textStatus, errorThrown) 
				{
					console.error(textStatus);
				}
			});
		}
		else if (type == "event")
		{
			var form = document.forms["create_event_form"];
			var username = sessionStorage.username;
			var id = form.event_id.value;
			var title = form.event_title.value;
			var highlights = form.event_highlights.value;
			var street_address = form.event_street_address.value;
			var city = form.event_city.value;
			var state = form.event_state.value;
			var zipcode = form.event_zipcode.value;
			var radius = form.event_radius.value;
			var from = form.event_from.value + " " + form.event_from_time.value;
			var to = form.event_to.value + " " + form.event_to_time.value;
			var url = form.event_url.value;
			var category = form.event_category.value;
			
			$.ajaxSetup ({
				cache: false
			});
	
			var loadUrl = AJAXManager.url + "announcement/event";
			var query = { username: username,
						  id: id,
						  title: title,
						  highlights: highlights,
					  	  street_address: street_address, 
					  	  city: city, 
					  	  state: state, 
					  	  zipcode: zipcode,
						  radius: radius,
						  from: from,
						  to: to,
					  	  url: url, 
					  	  category: category };
					  
			$.ajax ({
				type: "POST",
				url: loadUrl,
				data: query,
				dataType: "json",
				timeout: 5000, //5 seconds
				success: function(data) 
				{
					if (data.res == "TRUE")
					{
						var template = "<div class='alert alert-success'>";
						template += "<button type='button' class='close' data-dismiss='alert'>×</button>";
						template += "<strong>Success!</strong> Your announcement has been created/ updated.</div>";
						$("#create_announcement_alert").append(template);
					}
					else
					{
						var template = "<div class='alert alert-error'>";
						template += "<button type='button' class='close' data-dismiss='alert'>×</button>";
						template += "<strong>Error!</strong> Unable to create/ update your announcement.</div>";
						$("#create_announcement_alert").append(template);
					}
				},
				error: function(jqXHR, textStatus, errorThrown) 
				{
					console.error(textStatus);
				}
			});
		}
	}
};