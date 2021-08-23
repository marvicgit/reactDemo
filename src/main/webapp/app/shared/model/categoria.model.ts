import { ISubCategoria } from 'app/shared/model/sub-categoria.model';

export interface ICategoria {
  id?: number;
  nombre?: string;
  descripcion?: string | null;
  subCategorias?: ISubCategoria[] | null;
}

export const defaultValue: Readonly<ICategoria> = {};
