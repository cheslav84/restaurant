
  $(document).ready(function() {
    const registrationForm = document.querySelector('#registration-form');
    const hide_registrarion_form = registrationForm.classList.contains('registrationProcess');

    if ( hide_registrarion_form ) {
      $('#login-form').addClass('hidden');
      $('#registration-form').removeClass('hidden');
    }

});




$('#hide-login-btn').click(function() {
    $('#login-form').addClass('hidden');
    $('#registration-form').removeClass('hidden');
  });

  $('#show-login-btn').click(function() {
    $('#registration-form').addClass('hidden');
    $('#login-form').removeClass('hidden');
  });
  


const show_reg_pw_btn = document.querySelector('#show-reg-passwd')
const show_reg_pw_icon = show_reg_pw_btn.querySelector('img')
const pw_reg_input = document.querySelector('#reg-password')

show_reg_pw_btn.addEventListener('click', () => {
	pw_reg_input.type = pw_reg_input.type === 'password' 
		? 'text' 
		: 'password'

        show_reg_pw_icon.src = show_reg_pw_icon.src.includes('off') 
		? 'view/pictures/icons/visibility.png' 
		: 'view/pictures/icons/visibility_off.png'
})

const show_log_pw_btn = document.querySelector('#show-log-passwd')
const show_log_pw_icon = show_log_pw_btn.querySelector('img')
const pw_log_input = document.querySelector('#log-password')

show_log_pw_btn.addEventListener('click', () => {
	pw_log_input.type = pw_log_input.type === 'password' 
		? 'text' 
		: 'password'

        show_log_pw_icon.src = show_log_pw_icon.src.includes('off') 
		? 'view/pictures/icons/visibility.png' 
		: 'view/pictures/icons/visibility_off.png'
})

const show_conf_pw_btn = document.querySelector('#show-conf-passwd')
const show_conf_pw_icon = show_conf_pw_btn.querySelector('img')
const pw_conf_input = document.querySelector('#conf-password')

show_conf_pw_btn.addEventListener('click', () => {
	pw_conf_input.type = pw_conf_input.type === 'password' 
		? 'text' 
		: 'password'

        show_conf_pw_icon.src = show_conf_pw_icon.src.includes('off') 
		? 'view/pictures/icons/visibility.png' 
		: 'view/pictures/icons/visibility_off.png'
})