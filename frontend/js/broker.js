var amountOfMoney = 0;
var amountOfBitcoins = 0;
var actualPriceBitcoin = 0;
var regex = /^\d+(.\d{1,2})?$/;
var userId = "";

$('#buttonAmountOfMoney').click(function () {

    var moneyAdded = parseFloat(document.getElementById("amountOfMoney").value);
    if (regex.test(moneyAdded)) {
        if (moneyAdded >= 0) {
            amountOfMoney += moneyAdded;
            insertMoney(amountOfMoney);      
        }
        else {
            alert("Insert a positive number");
        }
    }
    else {
        alert("Maximum of 2 decimals");
    }
    getPriceBitcoin();
})

$('#buttonAmountOfBitcoins').click(function () {
    var bitcoinsPurchased = parseFloat(document.getElementById("amountOfBitcoins").value);

    if (regex.test(bitcoinsPurchased)) {
        if (amountOfMoney >= bitcoinsPurchased * actualPriceBitcoin) {
            
            getPriceBitcoin();
            amountOfBitcoins += bitcoinsPurchased;
            amountOfMoney -= (bitcoinsPurchased * actualPriceBitcoin).toFixed(2);
           
        
            insertBitcoins(amountOfBitcoins)
        }
        else {
            alert("You don't have enough money");
        }
    }
    else {
        alert("Maximum of 2 decimals");
    }
    getPriceBitcoin();
})
$('#organizeAuction').click(function () {
    var bitcoinsAuction = parseFloat(document.getElementById("bitcoinsAuction").value);
    var moneyAuction = parseFloat(document.getElementById("moneyAuction").value);
    var openingAuction = parseFloat(document.getElementById("openingAuction").value);
    var deadlineAuction = parseFloat(document.getElementById("deadlineAuction").value);
    
    //Opening auction
    var openingAuction = new Date($('#openingAuction').val());
    openingDay = openingAuction.getDate();
    openingMonth = openingAuction.getMonth() + 1;
    openingYear = openingAuction.getFullYear();
  
    //Deadline
    var deadlineAuction = new Date($('#deadlineAuction').val());
    deadlineDay = deadlineAuction.getDate();
    deadlineMonth = deadlineAuction.getMonth() + 1;
    deadlineYear = deadlineAuction.getFullYear();

    console.log("Bitcoins: " + bitcoinsAuction + "\nMoney: " + moneyAuction + "\nOpening date: " + [openingDay, openingMonth, openingYear].join('/') 
    + "\nDeadline: " + [deadlineDay, deadlineMonth, deadlineYear].join('/'))

    if(bitcoinsAuction > amountOfBitcoins) alert("You don't have enough bitcoins");
    if(openingAuction <= deadlineAuction && openingDay >= new Date().getDate()){
        if (regex.test(bitcoinsAuction) && regex.test(moneyAuction)) {

                organizeAuction(openingAuction, deadlineAuction, bitcoinsAuction, moneyAuction);  
        }
        else {
            alert("Maximum of 2 decimals");
        }
        getPriceBitcoin();
    }
    else {
        alert("You have to put a correct date");
    }
    
})

//Consultes a back-end
/**GET*/
$(document).ready(function () {
    $.ajax({
        headers: {'Authorization': localStorage.getItem('token')},
        url: "http://localhost:8080/brokers/me",
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            userId = data.id;
            getDate();
            getUserInfo();
            getPriceBitcoin();
            getBitcoinsBought();
            getActiveAuctions();
        },
        error: function() { alert('Failed!'); },

    });
});
function getUserInfo() {
    $.ajax({
        headers: {'Authorization': localStorage.getItem('token')},
        url: "http://localhost:8080/brokers/"+userId,
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            amountOfMoney=data.account.balance;
            amountOfBitcoins=data.account.bitcoins;
            document.getElementById("totalBitcoins").innerHTML = "Total Bitcoins: " + data.account.bitcoins.toFixed(2);
            document.getElementById("totalMoney").innerHTML = "Total Money: " + data.account.balance.toFixed(2) + "€";
        },
        error: function() { alert('Failed!'); },

    });
}
function getPriceBitcoin() {
    $.ajax({
        headers: {'Authorization': localStorage.getItem('token')},
        url: "http://localhost:8080/bitcoins/value",
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            actualPriceBitcoin = data.toFixed(2)
            document.getElementById("actualPriceBitcoin").innerHTML = " PRICE OF BITCOIN: " + actualPriceBitcoin + "€";
        },
        error: function() { alert('Failed!'); },

    });
}
function getBitcoinsBought() {
    $.ajax({
        headers: {'Authorization': localStorage.getItem('token')},
        url: "http://localhost:8080/brokers/purchases/"+userId,
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            const options = data.map((data, i) => {
           
                return `<div id="listBitcoinsBought" class="card">
                       <div class="card-body">
                           <h5 class="card-title">Purchase ${i+1}</h5>
                           <p class="card-text">Amount of money: ${data.amountOfMoney.toFixed(2)}€</p>
                           <p class="card-text">Number of bitcoins: ${data.amountOfBitcoins.toFixed(2)}</p>
                           <p class="card-text">Date Bought: ${formatDate(data.buyDate)}</p>
                       </div>
               </div>`;
           });
   
           document.querySelector("#listBitcoinsBought").insertAdjacentHTML("afterbegin", options);
        },
        error: function() { alert('Failed!'); },

    });

}
function getActiveAuctions() {
    $.ajax({
        headers: {'Authorization': localStorage.getItem('token')},
        url: 'http://localhost:8080/brokers/auctions/'+userId,
        type: 'GET',
        dataType: 'json',
        success: function (data) {
           
        const options = data.map((data, i) => {
            
            return `<div id="listActiveAuctions" class="card">
                   <div class="card-body">
                       <h5 class="card-title">Auction ${i+1}</h5>
                       <p class="card-text">Start bid: ${data.startBid}€</p>
                       <p class="card-text">Number of bitcoins: ${data.bitcoins} BTC</p>
                       <p class="card-text">Start date: ${formatDate(data.startDate)}</p>
                       <p class="card-text">Deadline: ${formatDate(data.endDate)}</p>
                   </div>
           </div>`;
       });

       document.querySelector("#listActiveAuctions").insertAdjacentHTML("afterbegin", options);
        },
        error: function() { alert('Error at active auctions!'); },

    });

}
function formatDate(strDate){
    var strDate = strDate.split("-");
    var jsDate = new Date(strDate[0], strDate[1] - 1, strDate[2].substr(0,2));

    openingDay = jsDate.getDate();
    openingMonth = jsDate.getMonth() + 1;
    openingYear = jsDate.getFullYear();

    return openingDay + "/" + openingMonth + "/" + openingYear
}
/*POST*/
function organizeAuction(openingAuction, deadlineAuction, bitcoinsAuction, moneyAuction){
    $.ajax({
        headers: {'Authorization': localStorage.getItem('token')},
        type:"POST",
        url:'http://localhost:8080/brokers/auctions/'+userId,
        contentType: 'application/json',
        data:JSON.stringify( {
            "brokerID": userId,
            "startDate": openingAuction,
            "endDate": deadlineAuction,
            "bitcoins": bitcoinsAuction,
            "startBid": moneyAuction,
        }),
        error : function(err) {
            console.log('Error!', err);
            console.log('error');
            alert('Error ' + err);
        },
        success: function(data) {
            amountOfBitcoins -= bitcoinsAuction;
            insertBitcoins(amountOfBitcoins)
            getUserInfo()
            document.getElementById("listActiveAuctions").innerHTML = "";
            getActiveAuctions()
            alert("Auction created succesfully!")
        },
    })
}
/*PUT*/
function insertMoney(amountOfMoney){
    $.ajax({
        headers: {'Authorization': localStorage.getItem('token')},
        type:"PUT",
        url:'http://localhost:8080/brokers/money/'+userId,
        contentType: 'application/json',
        data:JSON.stringify( {
            "id": userId, 
            "balance": amountOfMoney
        }),
        error : function(err) {
            console.log('Error!', err);
            alert('Error ' + err);
        },
        success: function(data) {
            getUserInfo()
            console.log("Data: " + data);
        },
    })
}
function insertBitcoins(amountBitcoins){
    $.ajax({
        headers: {'Authorization': localStorage.getItem('token')},
        type:"PUT",
        url: "http://localhost:8080/brokers/bitcoins/"+userId,
        contentType: 'application/json',
        data:JSON.stringify( {
            //"id": userId, 
        "bitcoins": amountOfBitcoins,
        "balance": amountOfMoney
        }),
        error : function(err) {
            console.log('Error!', err);
            alert('Error ' + err);
        },
        success: function(data) {
            insertMoney(amountOfMoney)
            updatePriceBitcoin(amountBitcoins)
            document.getElementById("listBitcoinsBought").innerHTML = "";
            getBitcoinsBought();
        },
    })
}
function updatePriceBitcoin(amountOfBitcoins){
    $.ajax({
        headers: {'Authorization': localStorage.getItem('token')},
        type:"POST",
        url:'http://localhost:8080/bitcoins/buy/'+amountOfBitcoins,
        contentType: 'application/json',
        data:JSON.stringify(null),
        error : function(err) {
            console.log('Error!', err);
            console.log('error');
            alert('Error ' + err);
        },
        success: function(data) {
            getPriceBitcoin()
        },
    })
    /*var http = new XMLHttpRequest();
    var url = "http://localhost:8080/bitcoins/buy/"+amountOfBitcoins;
    http.open("POST", url, true);
    http.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
 
    http.onreadystatechange = function () {
        if (http.readyState == 4 && http.status == 200) {
            getPriceBitcoin()
            
        }
    }
    http.send(null);*/   
}
function getDate(){
    var today = new Date();

    document.getElementById("openingAuction").value = today.getFullYear() + '-' + ('0' + (today.getMonth() + 1)).slice(-2) + '-' + ('0' + today.getDate()).slice(-2);


}
