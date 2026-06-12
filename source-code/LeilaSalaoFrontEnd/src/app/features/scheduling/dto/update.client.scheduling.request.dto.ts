export interface UpdateClientSchedulingRequestDto {
    origin: SchedulingOrigin | null
    servicesIds: string[]
}

export type SchedulingOrigin =
    | 'APP'
    | 'PHONE_NUMBER'