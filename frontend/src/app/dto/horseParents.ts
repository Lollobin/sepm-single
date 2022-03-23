import {Horse} from "./horse";

export interface HorseParents {
  id?: bigint;
  name: string;
  description?: string;
  dateOfBirth: Date;
  sex: Sex;
  father?: Horse;
  mother?: Horse;
}

type Sex = "male" | "female";
