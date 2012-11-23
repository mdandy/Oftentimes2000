// JavaScript Document

var AJAXManager =
{
	url:				"http://m2.cip.gatech.edu/d/maven/api/Oftentimes2000/",
	
	login: function()
	{
		var form = document.forms["signin_form"];
		var username = form.username.value;
		var password = form.password.value;
	
		$.ajaxSetup (
		{
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
		var form = document.forms["create_account_form"];
		var username = form.username.value;
		var password = form.password.value;
		var password2 = form.confirm_password.value;
		var user_type = form.user_type.value;
		
		if (password !== password2)
		{
			var template = "<div class='alert alert-error'>";
			template += "<button type='button' class='close' data-dismiss='alert'>×</button>";
			template += "<strong>Error!</strong> Password does not match.</div>";
			$("#create_account_alert").append(template);
			return;
		}
		
		if (username == "" || password == "")
		{
			var template = "<div class='alert alert-error'>";
			template += "<button type='button' class='close' data-dismiss='alert'>×</button>";
			template += "<strong>Error!</strong> Username and/ or password cannot be empty.</div>";
			$("#create_account_alert").append(template);
			return;
		}
	
		$.ajaxSetup (
		{
			cache: false
		});

		var loadUrl = "server/login.php";
		var query = { q : "register",
					  username: username, 
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
					if (user_type == "faculty")
						window.location.href = "index.html?register=faculty";
					else
						window.location.href = "index.html?register=student";
				}
				else
				{
					var template = "<div class='alert alert-error'>";
					template += "<button type='button' class='close' data-dismiss='alert'>×</button>";
					template += "<strong>Error!</strong> Unable to create account.</div>";
					$("#create_account_alert").append(template);
				}
			},
			error: function(jqXHR, textStatus, errorThrown) 
			{
				console.error(textStatus);
			}
		});
	}
};