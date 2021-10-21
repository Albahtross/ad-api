
document.addEventListener("DOMContentLoaded", function() {

    let xhr = new XMLHttpRequest();		

    // 2. define what happens during the AJAX call
    xhr.onreadystatechange = function() {

        if(xhr.readyState === 4) {	
            console.log(JSON.parse(xhr.responseText));		// JSON.parse() to parse JSON 

            var adArray = JSON.parse(xhr.responseText);	// creating an array of JSON ad objects

            // looping through the array and adding each element to our table
            adArray.forEach(adElement => {
                addAdToTable(adElement);
            });

        }
    }


    // 3. open the xhr call with the http request verb and the url
    xhr.open('GET', '/ad-api/ad/api');

    // 4. send the ajax call
    xhr.send();
});

// low-level DOM Manipulation
function addAdToTable(ad) {

    // creating all of our needed DOM elements
    var tr = document.createElement('tr');		
    var id = document.createElement('td');		
    var onClickURL = document.createElement('td'); 
    var description = document.createElement('td'); 	
    var relevanceScore = document.createElement('td');
    var companyName = document.createElement('td');
    var costPerClick = document.createElement('td');
    var duration = document.createElement('td');
    var clientFirstName = document.createElement('td');
    var clientLastName = document.createElement('td');
    
    var editButtonCell = document.createElement('td');
    var editButton = document.createElement("BUTTON");
    editButton.id = "edit" + ad.id;
    var editImage = document.createElement('i');
    editImage.classList.add("fa", "fa-edit");
    editButton.appendChild(editImage);
    editButtonCell.appendChild(editButton);
    
    var deleteButtonCell = document.createElement('td');
    var deleteButton = document.createElement("BUTTON");
    deleteButton.id = "trash"+ ad.id;
    var deleteImage = document.createElement('i');
    deleteImage.classList.add("fa", "fa-trash");
    deleteButton.appendChild(deleteImage);
    deleteButtonCell.appendChild(deleteButton);

    // adding data to the elements
    id.innerText = ad.id;
    id.id = "row" + ad.id + "id";
    
    onClickURL.innerText = ad.onClickURL;
    onClickURL.id = "row" + ad.id + "url";
    
    description.innerText = ad.description;
    description.id = "row" + ad.id + "description";
    
    relevanceScore.innerText = ad.relevanceScore;
    relevanceScore.id = "row" + ad.id + "relevanceScore"
    
    companyName.innerText = ad.companyName;
    companyName.id = "row" + ad.id + "companyName";
    
    costPerClick.innerText = ad.costPerClick;
    costPerClick.id = "row" + ad.id + "CPC";
    
    duration.innerText = ad.duration;
    duration.id = "row" + ad.id + "duration";
    
    clientFirstName.innerText = ad.firstName;
    clientFirstName.id = "row" + ad.id + "clientFirstName";
    
    clientLastName.innerText = ad.lastName;
    clientLastName.id = "row" + ad.id + "clientLastName";

    // add <td>'s to our <tr>
    tr.appendChild(id);
    tr.appendChild(onClickURL);
    tr.appendChild(description);
    tr.appendChild(relevanceScore);
    tr.appendChild(companyName);
    tr.appendChild(costPerClick);
    tr.appendChild(duration);
    tr.appendChild(clientFirstName);
    tr.appendChild(clientLastName);
    tr.appendChild(editButtonCell);
    tr.appendChild(deleteButtonCell);
    
    tr.id = "row" + ad.id;

    // add our <tr> to our <tbody>
    document.getElementById('ad-table-body').appendChild(tr);
    
    document.getElementById('trash' + ad.id).addEventListener('click', function(event) {
		event.preventDefault();
		console.log("deleting" + ad.id);
		let xhr = new XMLHttpRequest();		
	
	    // 2. define what happens during the AJAX call
	    xhr.onreadystatechange = function() {
	
	        if(xhr.readyState === 4) {	
				//don't need to receive any response for this
				console.log('row' + ad.id);
				document.getElementById('row' + ad.id).remove();
				
				document.getElementById('form-header').innerHTML = "<h1>Create a New Ad</h1>";
				document.getElementById('edit-ad-form').style.display = "none";
				document.getElementById('edit-ad-form').reset()
				document.getElementById('new-ad-form').style.display = "block";
				document.getElementById('new-ad-form').reset();
	        }
	    }
	
		var currentId = {
			id : ad.id 
		}; // put the id into a json object
	
	    // 3. open the xhr call with the http request verb and the url
	    xhr.open('DELETE', '/ad-api/ad/api');
	
	    // 4. send the ajax call
	    xhr.send(JSON.stringify(currentId)); //send the provided ad ID
	})
	
	document.getElementById('edit' + ad.id).addEventListener('click', function(event) {
		event.preventDefault();
		console.log("editing" + ad.id);
		
		document.getElementById('form-header').innerHTML = "<h1>Edit an Existing Ad</h1>";
		document.getElementById('new-ad-form').style.display = "none";
		document.getElementById('edit-ad-form').style.display = "block";
		//Scroll to the form
		document.body.scrollTop = 0; // For Safari
  		document.documentElement.scrollTop = 0; // For Chrome, Firefox, IE and Opera

		//prefill form values with table row values
		
		document.getElementById('edit-ad-id').value = document.getElementById('row'+ad.id + 'id').innerHTML;
		document.getElementById('edit-ad-url').value = document.getElementById('row' + ad.id + 'url').innerHTML;		
		document.getElementById('edit-ad-description').value = document.getElementById("row" + ad.id + "description").innerHTML;
	    document.getElementById('edit-ad-relevance-score').value = document.getElementById("row" + ad.id + "relevanceScore").innerHTML;	
	    document.getElementById('edit-ad-company-name').value = document.getElementById("row" + ad.id + "companyName").innerHTML;	
	    document.getElementById('edit-ad-cpc').value = document.getElementById("row" + ad.id + "CPC").innerHTML;	
	    document.getElementById('edit-ad-duration').value = document.getElementById("row" + ad.id + "duration").innerHTML;	
	    document.getElementById('edit-ad-first-name').value = document.getElementById("row" + ad.id + "clientFirstName").innerHTML;	
	    document.getElementById('edit-ad-last-name').value = document.getElementById("row" + ad.id + "clientLastName").innerHTML;	
		
	})
	
}

document.getElementById('cancel').addEventListener('click', function(event){
	// Clear off fields and reset to the default (new ad form)
	console.log('cancel and reset form');
	event.preventDefault();
	document.getElementById('edit-ad-form').style.display = "none";
	document.getElementById('edit-ad-form').reset();
	document.getElementById('new-ad-form').style.display = "block";
	document.getElementById('new-ad-form').reset();
});

document.getElementById('edit-ad-form').addEventListener('submit', function(event){
	event.preventDefault();
	// get the data from the form
	var idOnForm = document.getElementById('edit-ad-id').value;
    var urlOnForm = document.getElementById('edit-ad-url').value;		
    var descriptionOnForm = document.getElementById('edit-ad-description').value;	
    var relevanceScoreOnForm = document.getElementById('edit-ad-relevance-score').value;
    var companyNameOnForm = document.getElementById('edit-ad-company-name').value;
    var cpcOnForm = document.getElementById('edit-ad-cpc').value;
    var durationOnForm = document.getElementById('edit-ad-duration').value;
    var clientFirstNameOnForm = document.getElementById('edit-ad-first-name').value;
    var clientLastNameOnForm = document.getElementById('edit-ad-last-name').value;

    // ES6+ allows for object literal syntax: basically JSON objects on the fly
    var ad = {
		id : idOnForm,
        onClickURL : urlOnForm,
        description : descriptionOnForm,
        relevanceScore: relevanceScoreOnForm,
        companyName: companyNameOnForm,
        costPerClick: cpcOnForm,
        duration: durationOnForm,
        firstName: clientFirstNameOnForm,
        lastName: clientLastNameOnForm
    };
    
    // make AJAX call


    // 1. make an xhr object (ready state is 0)
    let xhr = new XMLHttpRequest();		// make HTTP requests

    // 2. define what happens during the AJAX call
    xhr.onreadystatechange = function() {
        
        if(xhr.readyState === 4) {	

            // getting back the updated ad object
            var updatedAd = JSON.parse(xhr.responseText);

			// remove old entry
			document.getElementById('row' + ad.id).remove();
            // adding the updated ad to our table
            addAdToTable(updatedAd);

            // reset the form
            document.getElementById('form-header').innerHTML = "<h1>Create a New Ad</h1>";
			document.getElementById('edit-ad-form').style.display = "none";
			document.getElementById('edit-ad-form').reset();
			document.getElementById('new-ad-form').style.display = "block";
			document.getElementById('new-ad-form').reset();
        }
    }


    // 3. open the xhr call with the http request verb and the url
    xhr.open('PUT', '/ad-api/ad/api');
    console.log(ad);

    // 4. send the ajax call
    xhr.send(JSON.stringify(ad));	// converting from variable to JSON and sending it in the POST request
});

document.getElementById('new-ad-form').addEventListener('submit', function(event) {
    event.preventDefault();		// prevent default form actions from occuring

    // get the data from the form
    var urlOnForm = document.getElementById('new-ad-url').value;		
    var descriptionOnForm = document.getElementById('new-ad-description').value;	
    var relevanceScoreOnForm = document.getElementById('new-ad-relevance-score').value;
    var companyNameOnForm = document.getElementById('new-ad-company-name').value;
    var cpcOnForm = document.getElementById('new-ad-cpc').value;
    var durationOnForm = document.getElementById('new-ad-duration').value;
    var clientFirstNameOnForm = document.getElementById('new-ad-first-name').value;
    var clientLastNameOnForm = document.getElementById('new-ad-last-name').value;

    // ES6+ allows for object literal syntax: basically JSON objects on the fly
    var ad = {
        onClickURL : urlOnForm,
        description : descriptionOnForm,
        relevanceScore: relevanceScoreOnForm,
        companyName: companyNameOnForm,
        costPerClick: cpcOnForm,
        duration: durationOnForm,
        firstName: clientFirstNameOnForm,
        lastName: clientLastNameOnForm
    };

    // make AJAX call


    // 1. make an xhr object (ready state is 0)
    let xhr = new XMLHttpRequest();		// make HTTP requests

    // 2. define what happens during the AJAX call
    xhr.onreadystatechange = function() {
        
        if(xhr.readyState === 4) {	

            // getting back the updated ad object
            var updatedAd = JSON.parse(xhr.responseText);

            // adding the updated ad to our table
            addAdToTable(updatedAd);

            // reset the form
            document.getElementById('new-ad-form').reset();
        }
    }


    // 3. open the xhr call with the http request verb and the url
    xhr.open('POST', '/ad-api/ad/api');
    console.log(ad);

    // 4. send the ajax call
    xhr.send(JSON.stringify(ad));	// converting from variable to JSON and sending it in the POST request
});




// example of button click event
// document.getElementById('button-id').addEventListener('click', function(event) {

// })