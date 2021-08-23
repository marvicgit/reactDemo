import dayjs from 'dayjs';
import { ICompraDetalle } from 'app/shared/model/compra-detalle.model';

export interface ICompra {
  id?: number;
  fecha?: string;
  total?: number | null;
  compraDetalles?: ICompraDetalle[] | null;
}

export const defaultValue: Readonly<ICompra> = {};
