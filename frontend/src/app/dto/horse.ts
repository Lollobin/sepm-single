export interface Horse {
  id?: bigint;
  name: string;
  description?: string;
  dateOfBirth: Date;
  sex: Sex;
  fatherId?: bigint;
  motherId?: bigint;
}

type Sex = "male" | "female";
