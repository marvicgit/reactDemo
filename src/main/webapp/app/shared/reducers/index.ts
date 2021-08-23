import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import locale, { LocaleState } from './locale';
import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from 'app/modules/administration/user-management/user-management.reducer';
import register, { RegisterState } from 'app/modules/account/register/register.reducer';
import activate, { ActivateState } from 'app/modules/account/activate/activate.reducer';
import password, { PasswordState } from 'app/modules/account/password/password.reducer';
import settings, { SettingsState } from 'app/modules/account/settings/settings.reducer';
import passwordReset, { PasswordResetState } from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import categoria from 'app/entities/categoria/categoria.reducer';
// prettier-ignore
import subCategoria from 'app/entities/sub-categoria/sub-categoria.reducer';
// prettier-ignore
import producto from 'app/entities/producto/producto.reducer';
// prettier-ignore
import variante from 'app/entities/variante/variante.reducer';
// prettier-ignore
import imagen from 'app/entities/imagen/imagen.reducer';
// prettier-ignore
import cliente from 'app/entities/cliente/cliente.reducer';
// prettier-ignore
import venta from 'app/entities/venta/venta.reducer';
// prettier-ignore
import ventaDetalle from 'app/entities/venta-detalle/venta-detalle.reducer';
// prettier-ignore
import tarjeta from 'app/entities/tarjeta/tarjeta.reducer';
// prettier-ignore
import compra from 'app/entities/compra/compra.reducer';
// prettier-ignore
import compraDetalle from 'app/entities/compra-detalle/compra-detalle.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const rootReducer = {
  authentication,
  locale,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  categoria,
  subCategoria,
  producto,
  variante,
  imagen,
  cliente,
  venta,
  ventaDetalle,
  tarjeta,
  compra,
  compraDetalle,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar,
};

export default rootReducer;
