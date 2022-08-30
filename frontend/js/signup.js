$('#registerUserBtn').click(function () {

    var typeUser = document.getElementById("typeUser").value;
    var name = document.getElementById("inputName").value;
    var username = document.getElementById("inputSurname").value;
    var email = document.getElementById("inputEmail").value;
    var password = document.getElementById("inputPassword").value;

    if (typeUser == "Broker")
        createUser(name, username, email, password, "brokers");
    else if (typeUser == "Bidder")
        createUser(name, username, email, password, "bidders");


});

function createUser(p_name, p_surname, p_email, p_password,typeUser) {
    console.log(p_password)
    var http = new XMLHttpRequest();
    var url = "http://localhost:8080/"+typeUser;
    http.open("POST", url, true);
    http.setRequestHeader("Content-Type", "application/json;charset=UTF-8");

    http.onreadystatechange = function () {
        if (http.readyState == 4 && http.status == 200) {
            //aqui obtienes la respuesta de tu peticion
            console.log("Afegit")
            alert(http.responseText);   
        }
    }
   
    http.send(JSON.stringify({
        "name": p_name, 
        "surname": p_surname, 
        "email": p_email, 
        "password": p_password
    }));

}