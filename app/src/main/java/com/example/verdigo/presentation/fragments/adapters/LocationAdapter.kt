import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import com.example.verdigo.R
import com.example.verdigo.data.model.Location

class LocationAdapter(
    private val context: Context,
    private val locations: List<Location>,
    private val onViewClicked: (Location) -> Unit
) : ArrayAdapter<Location>(context, 0, locations) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.fragment_map_item, parent, false)

        val location = locations[position]
        val locationText: TextView = view.findViewById(R.id.location_text)
        val viewButton: Button = view.findViewById(R.id.view_button)

        locationText.text = location.address
        viewButton.setOnClickListener {
            onViewClicked(location)
        }

        return view
    }
}