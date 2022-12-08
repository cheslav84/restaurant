// window.onload=function(){
//     // var auto = setTimeout(function(){ autoRefresh(); }, 100);

//     $.ajax({
//         type: "GET",
//         url: "/index",
//         data: "{menu: '"+ coffee +"' , param2 :'"+ param2 +"'}",
//         contentType: "application/json; charset=utf-8",
//         dataType: "json",
//         async: false,
//         cache: false,
//         success: function(result){
//        //Successfully gone to the server and returned with the string result of the server side function do what you want with the result  
//        alert('test');
//         }
//         ,error(er)
//         {
//         //Faild to go to the server alert(er.responseText)
//         }
// });
// }


// window.onload=function(){
//     // var auto = setTimeout(function(){ autoRefresh(); }, 100);

//     function submitform(){
//       alert('test');
//       document.forms["coffeeMenuButton"].submit();
//     }

//     function autoRefresh(){
//        clearTimeout(auto);
//        auto = setTimeout(function(){ submitform(); autoRefresh(); }, 10000);
//     }

// }




// $(document).ready( function () {
//     getDishesList();
//   });


// function getDishesList(){
//     document.getElementById("coffeeMenuButton").submit();


// }



// $('#coffeeMenuButton').on('beforeSubmit', function (e) {
//     if (!confirm("Everything is correct. Submit?")) {
//         return false;
//     }
//     return true;
// });