import dayjs from 'dayjs';
import { IVentaDetalle } from 'app/shared/model/venta-detalle.model';
import { ICliente } from 'app/shared/model/cliente.model';

export interface IVenta {
  id?: number;
  fecha?: string;
  descuento?: number | null;
  total?: number | null;
  ventaDetalles?: IVentaDetalle[] | null;
  cliente?: ICliente | null;
}

export const defaultValue: Readonly<IVenta> = {};
