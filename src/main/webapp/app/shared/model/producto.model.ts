import { ICompraDetalle } from 'app/shared/model/compra-detalle.model';
import { IVentaDetalle } from 'app/shared/model/venta-detalle.model';
import { IImagen } from 'app/shared/model/imagen.model';
import { IVariante } from 'app/shared/model/variante.model';
import { ISubCategoria } from 'app/shared/model/sub-categoria.model';

export interface IProducto {
  id?: number;
  nombre?: string;
  descripcion?: string | null;
  precio?: number;
  stock?: number | null;
  tallas?: string | null;
  colores?: string | null;
  tags?: string | null;
  venta?: boolean | null;
  descuento?: number | null;
  nuevo?: boolean | null;
  compraDetalles?: ICompraDetalle[] | null;
  ventaDetalles?: IVentaDetalle[] | null;
  imagens?: IImagen[] | null;
  variantes?: IVariante[] | null;
  subCategoria?: ISubCategoria | null;
}

export const defaultValue: Readonly<IProducto> = {
  venta: false,
  nuevo: false,
};
