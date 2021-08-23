import { ICliente } from 'app/shared/model/cliente.model';

export interface ITarjeta {
  id?: number;
  numero?: string;
  vigencia?: string;
  codigo?: string;
  cliente?: ICliente | null;
}

export const defaultValue: Readonly<ITarjeta> = {};
