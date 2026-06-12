export interface SchedulingResponseDto {
    id: string
    nameClient: string
    phoneNumberClient: string
    dateHours: string
    status: SchedulingStatus
    observations: string | null
    origin: SchedulingOrigin | null
    priceCharged: number
    services: SchedulingServiceDto[]
}

export interface SchedulingServiceDto {
    id: string
    name: string
}

export type SchedulingStatus =
    | 'SCHEDULED'
    | 'DONE'
    | 'CANCELLED'

export type SchedulingOrigin =
    | 'APP'
    | 'PHONE_NUMBER'