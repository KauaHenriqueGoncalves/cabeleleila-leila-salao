export interface CreateClientDto {
    user: {
        email: string | null | undefined;
        password: string | null | undefined;
    }
    name: string | null | undefined;
    phoneNumber: string | null | undefined;
}