import Controller from '@ember/controller';
import ENV from '../../config/environment';
import { action } from '@ember/object';

export default class LoginController extends Controller {
  @action login() {
    fetch(`${ENV.APP.API}/api/auth/v1/tokens`, {
      headers: {'Accept': 'application/json'},
      method: 'POST',
      body: JSON.stringify({
        username: this.username,
        password: this.password
      })
    }).then(function() {
      document.location = "/main";
    }).catch(function() {
      this.set("loginFailed", true);
    });
  }
}
