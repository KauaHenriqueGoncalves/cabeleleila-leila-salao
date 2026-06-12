export interface UpdateAdminSchedulingRequestDto {
    status: SchedulingStatus
    dateHours: string
    origin: SchedulingOrigin | null
    servicesIds: string[]
}

export type SchedulingOrigin =
    | 'APP'
    | 'PHONE_NUMBER'

export type SchedulingStatus =
    | 'SCHEDULED'
    | 'DONE'
    | 'CANCELLED'