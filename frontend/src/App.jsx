import { useEffect, useState } from "react";
import { getDucks, deleteDuck } from "./services/duckService";
import DuckTable from "./components/DuckTable";
import AddDuckForm from "./components/AddDuckForm";
import EditDuckForm from "./components/EditDuckForm";
import CreateOrderForm from "./components/CreateOrderForm";
import OrderResult from "./components/OrderResult";
import "./App.css";

function App() {

    const [ducks, setDucks] = useState([]);
    const [selectedDuck, setSelectedDuck] = useState(null);
    const [orderResult, setOrderResult] = useState(null);

    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");

    useEffect(() => {
        loadDucks();
    }, []);

    async function loadDucks() {

        try {

            setLoading(true);
            setError("");

            const data = await getDucks();

            setDucks(data);

        } catch (error) {

            setError(error.message);

        } finally {

            setLoading(false);
        }
    }

    async function handleDuckAdded() {
        await loadDucks();
    }

    function handleEdit(duck) {
        setSelectedDuck(duck);
    }

    async function handleDuckUpdated() {

        setSelectedDuck(null);

        await loadDucks();
    }

    function handleCancelEdit() {
        setSelectedDuck(null);
    }

    async function handleDelete(id) {

        const confirmed =
            window.confirm(
                "Are you sure you want to delete this duck?"
            );

        if (!confirmed) {
            return;
        }

        try {

            setError("");

            await deleteDuck(id);

            await loadDucks();

        } catch (error) {

            setError(error.message);
        }
    }

    function handleOrderCreated(order) {

        setOrderResult(order);

        window.scrollTo({
            top: document.body.scrollHeight,
            behavior: "smooth"
        });
    }

    return (
        <div className="app">

            <header className="topbar">

                <div className="brand">

                    <div className="brand-icon">
                        🦆
                    </div>

                    <div>
                        <h1>Duck Store</h1>
                        <span>Warehouse Management</span>
                    </div>

                </div>

                <div className="status">
                    <span className="status-dot"></span>
                    System Online
                </div>

            </header>


            <main className="container">

                <section className="hero">

                    <div>
                        <p className="eyebrow">
                            WAREHOUSE DASHBOARD
                        </p>

                        <h2>
                            Manage your duck inventory
                        </h2>

                        <p className="hero-text">
                            Add, update and manage warehouse stock,
                            then calculate shipping prices for orders.
                        </p>
                    </div>

                    <div className="inventory-card">

                        <span>
                            Active Ducks
                        </span>

                        <strong>
                            {ducks.length}
                        </strong>

                    </div>

                </section>


                {error && (
                    <div className="alert error">
                        <span>⚠</span>
                        {error}
                    </div>
                )}


                <section className="section-card">

                    <div className="section-header">

                        <div>
                            <p className="section-label">
                                INVENTORY
                            </p>

                            <h3>
                                Add Duck
                            </h3>

                            <p>
                                Add new warehouse stock or merge it
                                with existing inventory.
                            </p>
                        </div>

                    </div>

                    <AddDuckForm
                        onDuckAdded={handleDuckAdded}
                    />

                </section>


                {selectedDuck && (
                    <section className="section-card edit-card">

                        <div className="section-header">

                            <div>
                                <p className="section-label">
                                    INVENTORY
                                </p>

                                <h3>
                                    Edit Duck
                                </h3>

                                <p>
                                    Update price and available quantity.
                                </p>
                            </div>

                        </div>

                        <EditDuckForm
                            duck={selectedDuck}
                            onDuckUpdated={handleDuckUpdated}
                            onCancel={handleCancelEdit}
                        />

                    </section>
                )}


                <section className="section-card">

                    <div className="section-header">

                        <div>
                            <p className="section-label">
                                INVENTORY
                            </p>

                            <h3>
                                Warehouse Stock
                            </h3>

                            <p>
                                Active ducks sorted by quantity.
                            </p>
                        </div>

                        <div className="count-badge">
                            {ducks.length} items
                        </div>

                    </div>


                    {loading ? (

                        <div className="loading">
                            <div className="spinner"></div>
                            Loading inventory...
                        </div>

                    ) : (

                        <DuckTable
                            ducks={ducks}
                            onEdit={handleEdit}
                            onDelete={handleDelete}
                        />

                    )}

                </section>


                <section className="section-card order-section">

                    <div className="section-header">

                        <div>
                            <p className="section-label">
                                ORDER
                            </p>

                            <h3>
                                Calculate Order
                            </h3>

                            <p>
                                Select a duck, destination and shipping
                                method to calculate the final price.
                            </p>
                        </div>

                    </div>

                    <CreateOrderForm
                        onOrderCreated={handleOrderCreated}
                    />

                </section>


                {orderResult && (
                    <section className="section-card result-card">

                        <OrderResult
                            order={orderResult}
                        />

                    </section>
                )}

            </main>


            <footer className="footer">

                <span>
                    🦆 Duck Store
                </span>

                <span>
                    Warehouse & Order Management
                </span>

            </footer>

        </div>
    );
}

export default App;