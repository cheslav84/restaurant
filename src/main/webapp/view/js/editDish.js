  $(document).ready(function() {
    const orderInfo = document.querySelector('#hide-order-info');
    const showDishInfo = orderInfo.classList.contains('showDishInfo');


    if ( showDishInfo ) {
        $('#hide-order-info').removeClass('hidden');
    }
});

  $('#close-cross-button').click(function() {
    $('#hide-order-info').addClass('hidden');
    $('#hide-order-info').removeClass('notHidden');
  });