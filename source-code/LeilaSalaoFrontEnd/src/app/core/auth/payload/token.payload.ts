export interface TokenPayload {
  id: string | null | undefined;
  role: string | null | undefined;
  sub: string | null | undefined;
  exp: number | null | undefined;
}