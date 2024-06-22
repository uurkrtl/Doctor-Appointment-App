import React, {useState} from 'react';
import {useNavigate, useParams} from "react-router-dom";
import AppointmentService from "../services/AppointmentService.ts";

function AppointmentDetailAdd() {
    const { id = '' } = useParams<string>();
    const [imageUrl, setImageUrl] = useState<string>('');
    const [description, setDescription] = useState<string>('');
    const navigate = useNavigate();
    const appointmentService = new AppointmentService();
    const [errorMessage, setErrorMessage] = useState<string>('');

    const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        appointmentService.updateAppointmentScore(id, imageUrl, description)
            .then(response => {
                console.log(response)
                navigate(`/appointments/add-date/${response.data.id}`)
            })
            .catch(error => {
                if (error.response) {
                    console.log(error.response.data);
                    setErrorMessage(error.response.data.message);
                }else {
                    console.log('Etwas ist schief gelaufen:', error.message);
                    setErrorMessage('Etwas ist schief gelaufen: ' + error.message);
                }
            });
    }

    return (
        <main className={'container'}>
            <div className="py-2 text-center">
                <svg xmlns="http://www.w3.org/2000/svg" width="72" height="72" fill="currentColor"
                     className="bi bi-calendar2-plus-fill" viewBox="0 0 16 16">
                    <path
                        d="M3.5 0a.5.5 0 0 1 .5.5V1h8V.5a.5.5 0 0 1 1 0V1h1a2 2 0 0 1 2 2v11a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2V3a2 2 0 0 1 2-2h1V.5a.5.5 0 0 1 .5-.5M2 3.5v1c0 .276.244.5.545.5h10.91c.3 0 .545-.224.545-.5v-1c0-.276-.244-.5-.546-.5H2.545c-.3 0-.545.224-.545.5m6.5 5a.5.5 0 0 0-1 0V10H6a.5.5 0 0 0 0 1h1.5v1.5a.5.5 0 0 0 1 0V11H10a.5.5 0 0 0 0-1H8.5z"/>
                </svg>
                <h2>Neuer Termin</h2>
            </div>

            <div className="row g-5">
                <div className="col-md-12 col-lg-12">
                    <form onSubmit={handleSubmit}>
                        <div className="row g-3">

                            <div className="col-sm-6">
                                <label htmlFor="imageUrl" className="form-label">Bild-URL</label>
                                <input type="text" className="form-control" id="imageUrl"
                                       placeholder="Schreiben Sie Bild Url"
                                       onChange={(e) => setImageUrl(e.target.value)}/>
                            </div>

                            <div className="col-sm-6">
                                <label htmlFor="description" className="form-label">Beschreibung</label>
                                <input type="text" className="form-control" id="description"
                                       placeholder="Erklären Sie Ihre Krankheit"
                                       onChange={(e) => setDescription(e.target.value)}/>
                            </div>

                        </div>

                        <button className="w-100 btn btn-primary btn-lg my-4" type="submit">Weiter</button>
                    </form>

                    {errorMessage && (
                        <div className="alert alert-danger" role="alert">
                            {errorMessage}
                        </div>
                    )}

                </div>
            </div>
        </main>
    );
}

export default AppointmentDetailAdd;