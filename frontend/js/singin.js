$(document).ready(function () {
    $("#signInBtn").click(() => {

        var settings = {
            "url": "http://localhost:8080/login",
            "method": "POST",
            "timeout": 0,
            "data": JSON.stringify( {
                "username": $("#inputEmail").val(),
                "password": $("#inputPassword").val()
            }),
            "success": function (data, textStatus, request) {
                console.log('success: ',  request.getAllResponseHeaders());
            },
            "error": function (request, textStatus, errorThrown) {
                console.log('error: ' + textStatus + ' headers: ' + request.getAllResponseHeaders() + ' ErrorThrown: ' + errorThrown);
            }
        };

        $.ajax(settings).done(function (data, textStatus, request) {
            localStorage.setItem('token', request.getResponseHeader('authorization'))
            var typeUser = document.getElementById("typeUser").value;
            if (typeUser == "Broker")
                window.location.href = "http://127.0.0.1:5500/html/profileBroker.html";
            else if (typeUser == "Bidder")
            window.location.href = "http://127.0.0.1:5500/html/profileBidder.html";
            console.log('Done Response. Data: ', request.getResponseHeader('authorization'));
        });
        return false;  //this is very important because of click. If not here, the page is reloaded and the ajax callback is never called.
    });
});

