$(document).ready(function () {
    $.ajax({
        headers: {'Authorization': localStorage.getItem('token')},
        url: "http://localhost:8080/brokers/me",
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            document.getElementById("name").innerHTML = data.name + " " + data.surname;
            document.getElementById("email").innerHTML = data.email;
        },
        error: function() { alert('Failed!'); },

    });
});
