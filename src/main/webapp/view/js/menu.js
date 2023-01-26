$(document).ready(function() {
    if (localStorage.selectVal) {
        $('select').val( localStorage.selectVal );
    }
});

$('select').on('change', function(){
    var currentVal = $(this).val();
    localStorage.setItem('selectVal', currentVal );
});