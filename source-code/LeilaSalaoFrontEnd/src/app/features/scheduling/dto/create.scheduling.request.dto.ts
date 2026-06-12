export interface CreateSchedulingRequestDto {
    dateHours: string
    observations: string | null | undefined
    origin: SchedulingOrigin | null
    servicesIds: string[]
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