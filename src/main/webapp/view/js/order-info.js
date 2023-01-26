  $(document).ready(function() {
    const orderInfo = document.querySelector('#hide-order-info');
    const showDishInfo = orderInfo.classList.contains('showDishInfo');



    // const last = Array.from(
    //   document.querySelectorAll('#delimiter')
    // ).pop();

    // let firstItem = Array.from(document.querySelectorAll('.dishes-amount-item')).pop();

    // firstItem.setAttribute('selected');

    // alert();



    if ( showDishInfo ) {
        $('#hide-order-info').removeClass('hidden');
    }
});

  $('#close-cross-button').click(function() {
    $('#hide-order-info').addClass('hidden');
    $('#hide-order-info').removeClass('notHidden');
  });