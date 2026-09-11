function DuckTable({ ducks, onEdit, onDelete }) {

    function getColorClass(color) {

        return `color-${color.toLowerCase()}`;
    }

    if (ducks.length === 0) {

        return (
            <div className="empty-state">

                <div className="empty-state-icon">
                    🦆
                </div>

                <strong>
                    No ducks in the warehouse
                </strong>

                <span>
                    Add your first duck using the form above.
                </span>

            </div>
        );
    }

    return (
        <div className="table-wrapper">

            <table className="duck-table">

                <thead>
                    <tr>
                        <th>Id</th>
                        <th>Color</th>
                        <th>Size</th>
                        <th>Price</th>
                        <th>Quantity</th>
                        <th>Actions</th>
                    </tr>
                </thead>

                <tbody>

                    {ducks.map((duck) => (

                        <tr key={duck.id}>

                            <td>
                                #{duck.id}
                            </td>

                            <td>
                                <span
                                    className={`badge ${getColorClass(duck.color)}`}
                                >
                                    {duck.color}
                                </span>
                            </td>

                            <td>
                                <span className="badge size-badge">
                                    {duck.size}
                                </span>
                            </td>

                            <td className="price">
                                ${Number(duck.price).toFixed(2)}
                            </td>

                            <td className="quantity">
                                {duck.quantity}
                            </td>

                            <td>

                                <div className="actions">

                                    <button
                                        className="secondary-button"
                                        onClick={() => onEdit(duck)}
                                    >
                                        Edit
                                    </button>

                                    <button
                                        className="danger-button"
                                        onClick={() => onDelete(duck.id)}
                                    >
                                        Delete
                                    </button>

                                </div>

                            </td>

                        </tr>

                    ))}

                </tbody>

            </table>

        </div>
    );
}

export default DuckTable;