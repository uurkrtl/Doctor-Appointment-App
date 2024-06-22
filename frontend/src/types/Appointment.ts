export type Appointment = {
    id: string,
    firstName: string,
    lastName: string,
    appointmentDate: Date,
    complaintId: number,
    description: string,
    imageUrl: string,
    urgencyScore: number,
    status: string,
    createdAt: Date,
    updatedAt: Date
}