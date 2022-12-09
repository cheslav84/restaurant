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
//     var menuCategory = "COFFEE";
//     var id = '<%=session.getAttribute("id")%>';


    
//     '<%Session["UserName"] = "' + menuCategory + '"; %>';
//      alert(id);
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