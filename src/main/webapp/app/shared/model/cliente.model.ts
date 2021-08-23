import { ITarjeta } from 'app/shared/model/tarjeta.model';
import { IVenta } from 'app/shared/model/venta.model';

export interface ICliente {
  id?: number;
  nombres?: string;
  primerApellido?: string | null;
  segundoApellido?: string | null;
  telefono?: string | null;
  email?: string | null;
  direccion?: string | null;
  referencia?: string | null;
  latitude?: number | null;
  longitude?: number | null;
  tarjetas?: ITarjeta[] | null;
  ventas?: IVenta[] | null;
}

export const defaultValue: Readonly<ICliente> = {};
