$(document).ready(function(){
  $("#btn-login").click(function(){
    var username = $('#inputUsername').val();
    var password = $('#inputPassword').val();
    var payload = {username: username, password: password};

    $.ajax({
      type: 'POST',
      url: "http://sshahirs.aka.corp.amazon.com/login",
      data: JSON.stringify(payload),
      dataType: "json",
      contentType: "application/json"
    })
    .fail(function(data){
      alert("Username/Password Incorrect, try again");
    })
    .done(function(data){
      localStorage.jwt = data.token;
      var username = JSON.parse(atob(data.token.split('.')[1]))['sub'];
      localStorage.username = username;
      window.location.replace("track_pet.html");
    })
  })
});
