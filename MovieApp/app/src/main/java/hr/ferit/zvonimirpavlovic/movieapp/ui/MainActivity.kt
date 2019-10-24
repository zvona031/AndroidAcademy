package hr.ferit.zvonimirpavlovic.movieapp.ui

import android.view.MenuItem
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_movies.*
import hr.ferit.zvonimirpavlovic.movieapp.R
import hr.ferit.zvonimirpavlovic.movieapp.common.showFragment
import hr.ferit.zvonimirpavlovic.movieapp.ui.base.BaseActivity
import hr.ferit.zvonimirpavlovic.movieapp.ui.favorite_movies.FavoriteMoviesFragment
import hr.ferit.zvonimirpavlovic.movieapp.ui.popular_movies.PopularMoviesFragment
import hr.ferit.zvonimirpavlovic.movieapp.ui.top_movies.TopMoviesFragment

class MainActivity : BaseActivity() {

    override fun getLayoutResourceId(): Int = R.layout.activity_movies

    override fun setUpUi() {
        initMoviesGridFragment()
    }

    private fun initMoviesGridFragment(){
        showFragment(R.id.mainFragmentHolder,
            PopularMoviesFragment()
        )
        bottomNavigation.setOnNavigationItemSelectedListener { selectFragment(it) }
    }

    private fun selectFragment(it: MenuItem): Boolean {
        var selectedFragment: Fragment? = null
        when (it.itemId) {
            R.id.navPopular -> selectedFragment =
                PopularMoviesFragment()
            R.id.navFavorite -> selectedFragment = FavoriteMoviesFragment()
            R.id.navTop -> selectedFragment = TopMoviesFragment()
        }
        if (selectedFragment != null) {
            showFragment(R.id.mainFragmentHolder,selectedFragment)
        }
        return true
    }

}
