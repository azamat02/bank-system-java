$(document).ready(function (){
    // For profile page
    $("#change_pass_form").hide()
    $("#error_message").hide()
    $("#change_pass_button").click(()=>{
        $("#change_pass_form").slideToggle(1000)
        $("#change_pass_button").slideUp();
    })
    $("#change_pass").keyup(check_passwords)
    $("#change_pass2").keyup(check_passwords)
    function check_passwords(){
        let pass1 = $("#change_pass").val()
        let pass2 = $("#change_pass2").val()
        let notEqualCheck = false
        let lengthCheck = false
        let error_message = ""

        if (pass1 != pass2){
            error_message = "Entered passwords different!"
            notEqualCheck = true
        } else {
            notEqualCheck = false
        }
        if (pass1.length <3 || pass1.length>30){
            error_message = "Password should be between 3 and 30 charachters!"
            lengthCheck = true
        } else {
            lengthCheck = false
        }
        if (lengthCheck == false && notEqualCheck == false){
            $("#submit_button").prop('disabled', false);
            $("#submit_button").addClass("button-primary")
            $("#submit_button").removeClass("button")
            $("#error_message").hide()
        } else {
            $("#submit_button").prop('disabled', true);
            $("#submit_button").removeClass("button-primary")
            $("#submit_button").addClass("button")
            $("#error_message").show()
            $("#error_message").text(error_message)
        }
    }

    // For online payment page
    $(".mobile_service").hide()
    $(".house_service").hide()
    $(".charity_service").hide()

    $("#back_button").hide()

    $("#mobile_service").click(()=>{
        $(".org").slideUp()
        $("#back_button").show()
        $(".mobile_service").slideDown()
    })

    $("#house_service").click(()=>{
        $(".org").slideUp()
        $("#back_button").show()
        $(".house_service").slideDown()
    })

    $("#charity_service").click(()=>{
        $(".org").slideUp()
        $("#back_button").show()
        $(".charity_service").slideDown()
    })

    $("#back_button").click(()=>{
        $(".mobile_service").hide()
        $(".house_service").hide()
        $(".charity_service").hide()

        $(".org").slideDown()
        $("#back_button").hide()
    })

    // For transfer page
    $("#for-another-client-bank").hide()
    $("#for-this-client-bank").hide()
    $("#for-between-own-cards").hide()
    $("#for-convertation").hide()
    $("#back-button").hide()

    $("#to-another-client-bank").click(()=>{
        $("#back-button").show()
        $(".operations").slideUp()
        $("#for-another-client-bank").slideDown()
    })

    $("#to-this-client-bank").click(()=>{
        $("#back-button").show()
        $(".operations").slideUp()
        $("#for-this-client-bank").slideDown()
    })

    $("#to-between-own-cards").click(()=>{
        $("#back-button").show()
        $(".operations").slideUp()
        $("#for-between-own-cards").slideDown()
    })

    $("#convertation").click(()=>{
        $("#back-button").show()
        $(".operations").slideUp()
        $("#for-convertation").slideDown()
    })

    $("#back-button").click(()=>{
        $("#for-another-client-bank").hide()
        $("#for-this-client-bank").hide()
        $("#for-between-own-cards").hide()
        $("#for-convertation").hide()
        $(".operations").slideDown()
        $("#back-button").hide()
    })

    $("#error-msg").hide()
    $("#card_number2").keyup(()=>{
        let toCardNumber = $("#card_number2").val()
        //Check if card exist
        $.ajax({
            url: 'http://localhost:8080/payment/card_exist',
            method: 'get',
            data: {cardNumber: toCardNumber},
            success: function(data){
                if (data=="exist"){
                    console.log("Entered card number exist!")
                    $("#error-msg").slideUp()
                    $("#pay-button").prop('disabled', false);
                    $("#pay-button").addClass("button-primary");
                    $("#pay-button").removeClass("button");
                } else {
                    console.log("Entered card number does not exist!")
                    $("#error-msg").slideDown()
                    $("#error-msg").text("Entered card number does not exist!");
                    $("#pay-button").prop('disabled', true);
                    $("#pay-button").removeClass("button-primary");
                    $("#pay-button").addClass("button");
                }
            }
        });
    })

    // For admin panel
    $("#users_list").hide()
    $("#cards_list").hide()
    $("#operations_list").hide()


    $.ajax({
        url: 'http://localhost:8080/admin/get_cards',
        method: 'get',
        success: function(data){
            let counter1 = 0;
            while(data[counter1] != null){
                let id1 = data[counter1]['id'];
                let user_id1 = data[counter1]['user_id'];
                let card_number1 = data[counter1]['card_number'];
                let expired_date1 = data[counter1]['expired_date'];
                let cvv1 = data[counter1]['cvv'];
                let card_type1 = data[counter1]['card_type'];
                let balance1 = data[counter1]['balance'] + " &#8376;";
                let content1 =
                    `
                        <tr>
                            <td>${id1}</td>
                            <td>${user_id1}</td>
                            <td>${card_number1}</td>
                            <td>${expired_date1}</td>
                            <td>${cvv1}</td>
                            <td>${card_type1}</td>
                            <td>${balance1}</td>
                        </tr>
                    `
                $("#cards_list_table_body").html($("#cards_list_table_body").html()+content1)
                counter1++;
            }
        }
    });
    $.ajax({
        url: 'http://localhost:8080/admin/get_operations',
        method: 'get',
        success: function(data){
            let counter2 = 0;
            while(data[counter2]){
                let id2 = data[counter2]['id'];
                let user_id2 = data[counter2]['user_id'];
                let date2 = data[counter2]['date'];
                let operation2 = data[counter2]['operation'];
                let from_card2 = data[counter2]['from_card'];
                let status2 = data[counter2]['status'];
                let balance2 = data[counter2]['balance'] + " &#8376;";
                let content2 =
                    `
                        <tr>
                            <td>${id2}</td>
                            <td>${user_id2}</td>
                            <td>${date2}</td>
                            <td>${operation2}</td>
                            <td>${from_card2}</td>
                            <td>${status2}</td>
                            <td>${balance2}</td>
                        </tr>
                    `
                $("#operations_list_table").html($("#operations_list_table").html()+content2)
                counter2++;
            }
        }
    });

    $("#user_list_button").click(()=>{
        $("#users_list").slideToggle("slow");
    })
    $("#card_list_button").click(()=>{
        $("#cards_list").slideToggle("slow");
    })
    $("#operations_list_button").click(()=>{
        $("#operations_list").slideToggle("slow");
    })

})