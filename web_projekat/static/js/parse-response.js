function parseResponse (sSwitch, sResponse){
    switch (sSwitch){
        case "company":
            switch (sResponse){
                case "EX":
                    alert("Company with this name already exists!")
                    break
                case "OK-C":
                    alert("Company successfully created.")
                    break
                case "OK-U":
                    alert("Company successfully updated.")
                    break
                case "OK-D":
                    alert("Company successfully deleted.")
                    break
                case "ER":
                    alert("An error occurred.")
                    break
            }
            break
        case "ticket":
            switch (sResponse){
                case "OK-B":
                    alert("Ticket successfully booked.")
                    break
                case "OK-C":
                    alert("Ticket successfully created.")
                    break
                case "OK-D":
                    alert("Ticket successfully deleted.")
                    break
                case "OK-U":
                    alert("Ticket successfully updated.")
                    break
                case "ER-B":
                    alert("Ticket couldn't be booked.")
                    break
                case "ER-C":
                    alert("Ticket couldn't be created.")
                    break
                case "ER-D":
                    alert("Ticket couldn't be deleted.")
                    break
                case "ER-U":
                    alert("Ticket couldn't be updated.")
                    break
                case "ER":
                    alert("An error occurred.")
                    break
            }
            break
        case "reservation":
            switch (sResponse) {
                case "OK-C":
                    alert("Ticket reserved.")
                    break
                case "OK-D":
                    alert("Reservation deleted.")
                    break
                case "ER":
                    alert("An error occurred.")
                    break
            }

            break
        case "user":
            switch (sResponse){
                case "OK-C":
                    alert("User successfully created.")
                    break
                case "EX":
                    alert("Username taken.")
                    break
                case "ER-L":
                    alert("Invalid input!")
                    break
                case "ER":
                    alert("An error occurred.")
                    break;
            }
            break
        case "object":
            switch (sResponse){
                case "ER-EX":
                    if(confirm("Object has existential crisis.\nGo to main page?"))
                        window.location.replace('/')
                    break
            }
            break
    }
}