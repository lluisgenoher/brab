var amountOfMoney = 0
var blockedMoney = 0
var regex = /^\d+(.\d{1,2})?$/;
var userId = "";

$('#buttonAmountOfMoney').click(function () {

    var moneyAdded = parseFloat(document.getElementById("amountOfMoneyInput").value);

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
})



document.getElementById("listActiveAuctions").addEventListener("click", function (e) {

    if (e.target && e.target.nodeName == "BUTTON") {
        bitcoinsBid = $("#numBTC" + e.target.id).val()
        moneyBid = $("#numMoney" + e.target.id).val()
        idAuction = $("#idAuction" + e.target.id).text()
        minimumBid = $("#p_startingBid" + e.target.id).text().split(" ")[2]
        numberBTC = $("#p_numBitcoins" + e.target.id).text().split(" ")[3]
    
        if (bitcoinsBid > numberBTC) alert("Not enough bitcoins on the auction");
        else if (moneyBid / bitcoinsBid < minimumBid / numberBTC) alert("You are not respecting the minimum bid");
        else if (moneyBid > amountOfMoney) alert("You don't have enough money")
        else {
            if (regex.test(bitcoinsBid) && regex.test(moneyBid))
                doABid(idAuction, moneyBid, bitcoinsBid);
            else if (bitcoinsBid == "" || moneyBid == "") alert("You need to complete the values");
            else alert("Maximum of 2 decimals");
        }


    }
});

//Consultes a back-end
/**GET*/
$(document).ready(function () {
    $.ajax({
        headers: { 'Authorization': localStorage.getItem('token') },
        url: "http://localhost:8080/bidders/me",
        type: 'GET',
        dataType: 'json',
        success: function (data) {

            userId = data.id;
            getUserInfo()
            getActiveAuctions()
            getClosedAuctions()
        },
        error: function () { alert('Failed!'); },

    });
});

function getUserInfo() {
    $.ajax({
        headers: { 'Authorization': localStorage.getItem('token') },
        url: "http://localhost:8080/bidders/" + userId,
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            amountOfMoney = data.account.balance;
            blockedMoney = data.blockedMoney;
            amountOfBitcoins = data.account.bitcoins;
            document.getElementById("amountOfMoney").innerHTML = "Total Money: " + amountOfMoney.toFixed(2) + "€";
            document.getElementById("blockedMoney").innerHTML = "Blocked Money: " + blockedMoney.toFixed(2) + "€";
            document.getElementById("amountOfBitcoins").innerHTML = "Amount of Bitcoins: " + amountOfBitcoins.toFixed(2) + " BTC";
        },
        error: function () { alert('Failed!'); },

    });
}
function getActiveAuctions() {
    $.ajax({
        headers: { 'Authorization': localStorage.getItem('token') },
        url: 'http://localhost:8080/bidders/active-auctions/',
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            const options = data.map((data, i) => {

                return `<div id="listActiveAuctions" class="card">
                           <div class="card-body">
                               <h5 class="card-title">Auction ${i + 1}</h5>
                               <p id="idAuction${i + 1}" style="display: none">${data.auction_id}</p>
                               <p id="p_numBitcoins${i + 1}" class="card-text">Number of Bitcoins: ${data.bitcoins} BTC</p>
                               <p class="card-text">Deadline: ${formatDate(data.endDate)}</p>
                               <p id="p_startingBid${i + 1}" class="card-text">Starting bid: ${data.startBid} €</p>
                               <div class="input-group mb-3">
                                   <input id="numBTC${i + 1}" type="text" class="form-control" placeholder="Bitcoins" aria-label="Bitcoins" style="margin-right:5px">
                                   <input id="numMoney${i + 1}" type="text" class="form-control" placeholder="Money" aria-label="Money">
                                   <div class="input-group-append">
                                       <button id="${i + 1}" class="btn btn-outline-primary" type="button">Bid</button>
                                   </div>
                               </div>
                           </div>
                       </div>`;
            });

            document.querySelector("#listActiveAuctions").insertAdjacentHTML("afterbegin", options);
        },
        error: function () { alert('Failed!'); },

    });

}

function formatDate(strDate) {
    var strDate = strDate.split("-");
    var jsDate = new Date(strDate[0], strDate[1] - 1, strDate[2].substr(0, 2));

    openingDay = jsDate.getDate();
    openingMonth = jsDate.getMonth() + 1;
    openingYear = jsDate.getFullYear();

    return openingDay + "/" + openingMonth + "/" + openingYear
}
function getClosedAuctions() {
    $.ajax({
        headers: { 'Authorization': localStorage.getItem('token') },
        url: 'http://localhost:8080/bidders/bid/' + userId,
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            const options = data.map((data, i) => {
                var successAuction;
                if (data.bidAccepted) successAuction = 'bg-success'
                else successAuction = 'bg-danger'
                return `<div id="listClosedAuctions" class="card">
                            <div class="card ${successAuction}">
                                <div class="card-body">
                                    <h5 class="card-title">Auction ${data.auction_id.split("-")[0]}-...</h5>
                                    <p class="card-text">Bet of ${data.moneyBid}€ for ${data.bitcoins} BTC</p>
                                </div>
                            </div>
                        </div>
                       
                        `;
            });

            document.querySelector("#listClosedAuctions").insertAdjacentHTML("afterbegin", options);
        },
        error: function () { alert('Failed!'); },

    });
}

/*PUT*/
function insertMoney(amountOfMoney) {
    $.ajax({
        headers: { 'Authorization': localStorage.getItem('token') },
        type: "PUT",
        url: "http://localhost:8080/bidders/money/" + userId,
        contentType: 'application/json',
        data: JSON.stringify({
            "id": userId,
            "balance": amountOfMoney
        }),
        error: function (err) {
            console.log('Error!', err);
            alert('Error ' + err);
        },
        success: function (data) {
            getUserInfo();
        },
    })
}
function insertBlockedMoney(blockedMoney) {
    $.ajax({
        headers: { 'Authorization': localStorage.getItem('token') },
        type: "PUT",
        url: "http://localhost:8080/bidders/blocked-money/" + userId + "/" + blockedMoney,
        contentType: 'application/json',
        data: JSON.stringify(null),
        error: function (err) {
            console.log('Error!', err);
            console.log('error');
            alert('Error ' + err);
        },
        success: function (data) {
            getUserInfo();
        },
    })
}
/*POST*/
function doABid(idAuction, moneyBid, bitcoinsBid) {
    $.ajax({
        headers: { 'Authorization': localStorage.getItem('token') },
        type: "POST",
        url: 'http://localhost:8080/bidders/bid/' + idAuction,
        contentType: 'application/json',
        data: JSON.stringify({
            "idBidder": userId,
            "idAuction": idAuction,
            "moneyBid": moneyBid,
            "bitcoinsBid": bitcoinsBid
        }),
        error: function (err) {
            console.log('Error!', err);
            console.log('error');
            alert('Error ' + err);
        },
        success: function (data) {
            amountOfMoney -= moneyBid;
            blockedMoney += moneyBid;
            insertMoney(amountOfMoney);
            insertBlockedMoney(blockedMoney);
            getUserInfo()
            alert("You have bidded succesfully")
        },
    })
}
